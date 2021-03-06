package top.zbeboy.isy.service.export;

import org.apache.poi.ss.usermodel.Row;
import top.zbeboy.isy.domain.tables.pojos.GraduationPracticeCompany;
import top.zbeboy.isy.service.util.DateTimeUtils;
import top.zbeboy.isy.service.util.ExportUtils;

import java.util.List;

/**
 * Created by zbeboy on 2017/1/4.
 */
public class GraduationPracticeCompanyExport extends ExportUtils<GraduationPracticeCompany> {

    // 序号
    private int sequence = 0;

    public GraduationPracticeCompanyExport(List<GraduationPracticeCompany> data) {
        super(data);
    }

    @Override
    public void createHeader(Row row) {
        row.createCell(0).setCellValue("序号");
        row.createCell(1).setCellValue("学生姓名");
        row.createCell(2).setCellValue("专业班级");
        row.createCell(3).setCellValue("性别");
        row.createCell(4).setCellValue("学号");
        row.createCell(5).setCellValue("电话号码");
        row.createCell(6).setCellValue("qq邮箱");
        row.createCell(7).setCellValue("父母联系方式");
        row.createCell(8).setCellValue("班主任");
        row.createCell(9).setCellValue("班主任联系方式");
        row.createCell(10).setCellValue("实习单位名称");
        row.createCell(11).setCellValue("实习单位地址");
        row.createCell(12).setCellValue("实习单位联系人");
        row.createCell(13).setCellValue("实习单位联系人联系方式");
        row.createCell(14).setCellValue("校内指导教师");
        row.createCell(15).setCellValue("校内指导教师联系方式");
        row.createCell(16).setCellValue("实习开始时间");
        row.createCell(17).setCellValue("实习结束时间");
        row.createCell(18).setCellValue("承诺书");
        row.createCell(19).setCellValue("安全责任书");
        row.createCell(20).setCellValue("实践协议书");
        row.createCell(21).setCellValue("实习申请书");
        row.createCell(22).setCellValue("实习接收");
        row.createCell(23).setCellValue("安全教育协议");
        row.createCell(24).setCellValue("父母同意书");
    }

    @Override
    public void createCell(Row row, GraduationPracticeCompany graduationPracticeCompany) {
        sequence++;
        row.createCell(0).setCellValue(sequence);
        row.createCell(1).setCellValue(graduationPracticeCompany.getStudentName());
        row.createCell(2).setCellValue(graduationPracticeCompany.getCollegeClass());
        row.createCell(3).setCellValue(graduationPracticeCompany.getStudentSex());
        row.createCell(4).setCellValue(graduationPracticeCompany.getStudentNumber());
        row.createCell(5).setCellValue(graduationPracticeCompany.getPhoneNumber());
        row.createCell(6).setCellValue(graduationPracticeCompany.getQqMailbox());
        row.createCell(7).setCellValue(graduationPracticeCompany.getParentalContact());
        row.createCell(8).setCellValue(graduationPracticeCompany.getHeadmaster());
        row.createCell(9).setCellValue(graduationPracticeCompany.getHeadmasterContact());
        row.createCell(10).setCellValue(graduationPracticeCompany.getGraduationPracticeCompanyName());
        row.createCell(11).setCellValue(graduationPracticeCompany.getGraduationPracticeCompanyAddress());
        row.createCell(12).setCellValue(graduationPracticeCompany.getGraduationPracticeCompanyContacts());
        row.createCell(13).setCellValue(graduationPracticeCompany.getGraduationPracticeCompanyTel());
        row.createCell(14).setCellValue(graduationPracticeCompany.getSchoolGuidanceTeacher());
        row.createCell(15).setCellValue(graduationPracticeCompany.getSchoolGuidanceTeacherTel());
        row.createCell(16).setCellValue(DateTimeUtils.formatDate(graduationPracticeCompany.getStartTime(), "yyyy-MM-dd"));
        row.createCell(17).setCellValue(DateTimeUtils.formatDate(graduationPracticeCompany.getEndTime(), "yyyy-MM-dd"));
        row.createCell(18).setCellValue(dealByte(graduationPracticeCompany.getCommitmentBook()));
        row.createCell(19).setCellValue(dealByte(graduationPracticeCompany.getSafetyResponsibilityBook()));
        row.createCell(20).setCellValue(dealByte(graduationPracticeCompany.getPracticeAgreement()));
        row.createCell(21).setCellValue(dealByte(graduationPracticeCompany.getInternshipApplication()));
        row.createCell(22).setCellValue(dealByte(graduationPracticeCompany.getPracticeReceiving()));
        row.createCell(23).setCellValue(dealByte(graduationPracticeCompany.getSecurityEducationAgreement()));
        row.createCell(24).setCellValue(dealByte(graduationPracticeCompany.getParentalConsent()));
    }

    public String dealByte(Byte b) {
        if (b != null && b == 1) {
            return "已交";
        }
        return "未交";
    }
}
