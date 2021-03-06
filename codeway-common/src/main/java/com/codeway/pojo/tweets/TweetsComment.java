package com.codeway.pojo.tweets;

import com.codeway.pojo.BasePojo;
import com.codeway.pojo.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 吐槽，评论表
 **/
@ApiModel(value = "TweetsComment", description = "吐槽，评论表")
@Getter
@Setter
//@Entity
//@Table(name = "t_hotel", schema = "test", catalog = "")
public class TweetsComment extends BasePojo implements Serializable {

    /**
     * 前台搜索参数
     */
//	@TableField(exist = false)
//    @Transient
    private String nickName;

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id; // 吐槽评论ID

    @NotNull(message="评论内容不能为空")
    private String content; // 评论内容

    private User user;

    private String userId; // 评论人ID

    private Long likeNum; // 点赞数

    private Integer isVisible; // 是否可见

	@NotNull(message="推特主键不能为空")
    private String tweetsId; // 吐槽表id

    private String parentId; // 父级评论

}