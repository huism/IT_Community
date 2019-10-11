// 回复问题
function reply() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId,1,commentContent);
}
// 二级回复（评论回复）
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var commentContent = $("#reply-"+commentId).val();
    comment2target(commentId,2,commentContent);
}

// 发送评论请求：问题回复，回复评论（二级回复）
function comment2target(targetId,type,content) {
    if(!content){
        alert("回复内容不能为空~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            // console.log("response:"+response);
            if(response.code == 200){
                window.location.reload();
            }else {
                // 未登录，弹出登录提示
                if(response.code == 2003){
                    var isConfirmed = confirm(response.message);
                    if(isConfirmed){
                        // 跳转到登录页面
                        window.open("https://github.com/login/oauth/authorize?client_id=7684b49c37b5186cd189&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closed",true);
                    }
                }else{
                    // 其他错误
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

// 点击展开二级评论
function collapseComment(e) {

    var id = e.getAttribute("data-id");
    console.log(id);
    var comments = $("#comment-"+id);

    // 获取二级评论展开或折叠状态
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        // 折叠
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else {
        // 展开

        var subCommentContainer = $("#comment-"+id);
        if(subCommentContainer.children().length != 1){
            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            // 获取评论回复列表
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {

                    var img = $('<img/>', {
                        "class": "media-object img-rounded avatar-style user-photo",
                        "src": comment.user.avatarUrl
                    });

                    var mediaLeft = $('<div/>', {
                        "class": "media-left",
                        "style":"padding: 10px"
                    });
                    mediaLeft.append(img);

                    var mediaBody = $('<div/>', {
                        "class": "media-body"
                    });
                    var h5 = $('<h5/>', {
                        "html": comment.user.name
                    });
                    var spanContent = $('<span/>', {
                        "html": comment.content
                    });
                    var spanData = $('<span/>', {
                        "class":"pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    });
                    mediaBody.append(h5);
                    mediaBody.append(spanContent);
                    mediaBody.append(spanData);

                    var mediaElement = $('<div/>', {
                        "class": "media",
                        "style": "margin-top: 10px"
                    });
                    mediaElement.append(mediaLeft);
                    mediaElement.append(mediaBody);

                    var contentElement = $('<div/>', {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12"
                    });
                    contentElement.append(mediaElement);
                    subCommentContainer.prepend(contentElement);
                });
                comments.addClass("in");
                e.setAttribute("data-collapse","in");
                e.classList.add("active");
            });
        }
    }
}








