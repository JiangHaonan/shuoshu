define(function(require,exports,module){
    require('jquery');



    require.async('/static/plugins/jquery.validate',function(){
        require.async('/static/plugins/additional-methods',function(){
            $("#login").validate({
                rules:{
                },
                messages:{

                },
                errorElement:'em',
                showErrors:function(errorMap, errorList){
                },
                errorPlacement:function(error,element){},
                submitHandler:function(form){
                    require.async('jquery.form',function(){
                        $(form).ajaxSubmit({
                            type:"post",
                            dataType:"json",
                            success:function(data){
                                alert(11)
                                var resultMsg = data.msg||"";
                                if(data.code == 104){
                                    alert(resultMsg);
                                    /*var url = "/member/main.html"
                                    if (data.redirectURL != "") {
                                        url = data.redirectURL;
                                    }
                                    window.location.href = url;*/
                                }
                                else
                                {
                                    alert("fail")
                                    alert(resultMsg)
                                }
                            }
                        });
                    })
                }
            });


        })
    })



})

