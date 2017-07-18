define(function(require,exports,module){
    require('jquery');


    require.async('/static/plugins/jquery.validate',function(){
        require.async('/static/plugins/additional-methods',function(){
            $("#register").validate({
                submitHandler:function(form){
                    require.async('jquery.form',function(){
                        $(form).ajaxSubmit({
                            type:"post",
                            dataType:"json",
                            success:function(data){
                                var resultMsg = data.msg||"";
                                if(data.code == 100){
                                    window.location.href = "/user/registerSuccess.html";
                                }
                                else
                                {
                                    $(".error_msg").html(resultMsg);
                                }
                            }
                        });
                    })
                }
            });


        })
    })

})

