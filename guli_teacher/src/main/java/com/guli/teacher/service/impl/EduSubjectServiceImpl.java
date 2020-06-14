package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.result.EduException;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.entity.vo.TwoSubject;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guli
 * @since 2020-02-15
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importExl(MultipartFile file) {
        List<String> msg = new ArrayList<>();
        try {
            //1、获取文件流
            InputStream inputStream = file.getInputStream();
            //2、创建workbook
            Workbook workbook = new HSSFWorkbook(inputStream);
            //3 根据workbook获取sheet
            Sheet sheet = workbook.getSheetAt(0);
            //获取表格最后一个索引位置也就是行数
            int lastRowNum = sheet.getLastRowNum();
            System.err.println("获取行数：" + lastRowNum);
            if (lastRowNum <= 1) {
                msg.add("请填写数据");
                return msg;
            }
            //遍历行数
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row rowData = sheet.getRow(rowNum);
                //根据每一行获取列表数据
                //获取第一列
                Cell cellOne = rowData.getCell(0);
                //根据cell获取第一列里面内容
                if(cellOne == null) {
                    msg.add("第"+rowNum+"行第一列数据为空");
                    //跳到下一行进行操作
                    continue;
                }
                String cellOneValue = cellOne.getStringCellValue();
                if(StringUtils.isEmpty(cellOneValue)) {
                    msg.add("第"+rowNum+"行第一列数据为空");
                    //跳到下一行进行操作
                    continue;
                }

                //cellOneValue 是一级分类名称
                //判断添加的一级分类名称在数据库表里面是否有相同的，如果没有添加
                EduSubject eduSubject = this.existOneSubject(cellOneValue);
                //为了后面添加二级分类，一级分类id
                String pid = null;
                if(eduSubject == null) { //表没有相同的一级分类
                    //添加到数据库里面
                    EduSubject subjectOne = new EduSubject();
                    subjectOne.setTitle(cellOneValue);
                    subjectOne.setParentId("0");
                    baseMapper.insert(subjectOne);
                    //添加一级分类之后，获取添加之后id值
                    pid = subjectOne.getId();
                } else {
                    //有相同的一级分类
                    pid = eduSubject.getId();
                }

                //获取第二列
                Cell cellTwo = rowData.getCell(1);
                if(cellTwo==null) {
                    msg.add("第"+rowNum+"行第二列数据为空");
                    continue;
                }
                // 根据cell获取第二列里面内容
                String cellTwoValue = cellTwo.getStringCellValue();
                if(StringUtils.isEmpty(cellTwoValue)) {
                    msg.add("第"+rowNum+"行第二列数据为空");
                    continue;
                }
                //cellTwoValue 是二级分类名称
                EduSubject eduSubjectTwo = this.existTwoSubject(cellTwoValue, pid);
                if(eduSubjectTwo == null) {
                    //没有相同的二级分类，实现二级分类添加
                    EduSubject subjectTwo = new EduSubject();
                    subjectTwo.setTitle(cellTwoValue);
                    subjectTwo.setParentId(pid);
                    baseMapper.insert(subjectTwo);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public List<OneSubject> getTree() {
        List<OneSubject> oneSubjectList = new ArrayList<>();
        //1、把一级分类查询出来、并查询一级分类下的二级分类
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("parent_id",0);
        List<EduSubject> list = baseMapper.selectList(wrapper);
        for (EduSubject subject1 : list) {
            OneSubject os = new OneSubject();
            BeanUtils.copyProperties(subject1,os);
            //查询下级列表
            QueryWrapper wr = new QueryWrapper();
            wr.eq("parent_id",os.getId());
            List<EduSubject> twoList = baseMapper.selectList(wr);
            for (EduSubject subject2 : twoList) {
                TwoSubject ts = new TwoSubject();
                //复制
                BeanUtils.copyProperties(subject2,ts);
                os.getChildren().add(ts);
            }
            //添加到OneSubject集合中
            oneSubjectList.add(os);
        }

        return oneSubjectList;
    }
    @Override
    public boolean saveLevelOne(EduSubject subject) {

        EduSubject subjectLevelOne = this.selectSubjectByName(subject.getTitle());

        if(subjectLevelOne == null){
            return super.save(subject);
        }

        return false;
    }
    @Override
    public boolean saveLevelTwo(EduSubject subject) {
        EduSubject subjectLevelTwo = this.selectSubjectByNameAndParentId(subject.getTitle(), subject.getParentId());
        if(subjectLevelTwo == null){
            return this.save(subject);
        }else{
            throw new EduException(20001, "类别已存在");
        }
    }
    //2 判断二级分类名称是否有相同的
    private EduSubject existTwoSubject(String name,String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);

        return baseMapper.selectOne(wrapper);
    }

    //1 判断一级分类名称是否相同
    private EduSubject existOneSubject(String name) {
        //查询数据库判断
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    /**
     * 根据课程一级分类的名字查询是否存在
     * @param cellValue
     * @return
     */
    private EduSubject selectSubjectByName(String cellValue) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",cellValue);
        wrapper.eq("parent_id",0);
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }
    /**
     * 根据二级分类名称和parentID查询是否存在Subject
     * @param stringCellValue
     * @param pid
     * @return
     */
    private EduSubject selectSubjectByNameAndParentId(String stringCellValue, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",stringCellValue);
        wrapper.eq("parent_id",pid);
        EduSubject subject = baseMapper.selectOne(wrapper);
        return subject;
    }
}
