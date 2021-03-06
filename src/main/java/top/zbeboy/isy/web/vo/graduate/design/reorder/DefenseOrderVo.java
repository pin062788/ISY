package top.zbeboy.isy.web.vo.graduate.design.reorder;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by zbeboy on 2017/7/25.
 */
@Data
public class DefenseOrderVo {
    @NotNull
    @Size(max = 64)
    private String defenseOrderId;
    @NotNull
    @Size(max = 64)
    private String defenseGroupId;
    @NotNull
    @Size(max = 64)
    private String graduationDesignReleaseId;
    private int defenseStatus;
    private double grade;
    private int scoreTypeId;
    private String defenseQuestion;
}
