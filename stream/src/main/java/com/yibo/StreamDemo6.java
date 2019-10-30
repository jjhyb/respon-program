package com.yibo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: huangyibo
 * @Date: 2019/10/28 23:51
 * @Description: Stream收集器
 */
public class StreamDemo6 {

    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
                new Student("小明",10,Gender.MALE,Grade.ONE),
                new Student("大明",9,Gender.MALE,Grade.THREE),
                new Student("小白",10,Gender.FEMALE,Grade.TWO),
                new Student("小黑",7,Gender.FEMALE,Grade.FOUR),
                new Student("小黄",13,Gender.FEMALE,Grade.THREE),
                new Student("小红",11,Gender.MALE,Grade.ONE),
                new Student("小青",6,Gender.FEMALE,Grade.THREE),
                new Student("小紫",15,Gender.MALE,Grade.TWO),
                new Student("小王",5,Gender.MALE,Grade.ONE),
                new Student("小李",6,Gender.MALE,Grade.ONE),
                new Student("小马",13,Gender.FEMALE,Grade.FOUR),
                new Student("小刘",10,Gender.MALE,Grade.FOUR));

        //得到所有学生的年龄列表
        //stu -> stu.getAge() ---> Student::getAge   不会多生成一个类型lambda$0这样的函数
        List<Integer> ages1 = students.stream().map(Student::getAge).collect(Collectors.toList());
        System.out.println("所有学生的年龄：" + ages1);

        Set<Integer> ages2 = students.stream().map(Student::getAge).collect(Collectors.toSet());
        System.out.println("所有学生的年龄：" + ages2);

        Set<Integer> ages3 = students.stream().map(Student::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("所有学生的年龄：" + ages3);


        //统计汇总信息
        IntSummaryStatistics summaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.out.println("年龄汇总信息：" + summaryStatistics);


        //分块 按学生性别分块
        Map<Boolean, List<Student>> genderMap = students.stream().collect(Collectors.partitioningBy(stu -> stu.getGender() == Gender.MALE));
        System.out.println("男女学生列表：" + genderMap);


        //分组 按学生班级分组
        Map<Grade, List<Student>> gradeListMap = students.stream().collect(Collectors.groupingBy(Student::getGrade));
        System.out.println("班级学生列表：" + gradeListMap);


        //得到每个班级学生的个数
        Map<Grade, Long> gradeMap = students.stream().collect(Collectors.groupingBy(Student::getGrade, Collectors.counting()));
        System.out.println("班级学生个数列表：" + gradeMap);

    }
}
