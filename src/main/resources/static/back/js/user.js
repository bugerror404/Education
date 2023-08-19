
var tale1 = new $.tale();
function subUser(status) {
    var username = $('#registerForm input[name=username]').val();
    var password = $('#registerForm input[name=password]').val();
    var email = $('#registerForm input[name=email]').val();
    var identity=$('#registerForm select[name=identity]').val();
    if (username == '') {
        tale1.alertWarn('用户名不能为空！');
        return;
    }
    if (password=='') {
        tale1.alertWarn('密码不能为空！');
        return;
    }
    if (email == '') {
        tale1.alertWarn('请输入用户名和密码！');
        return;
    }
    if (identity == '') {
        tale1.alertWarn('请输入身份信息！');
        return;
    }
    var params = $("#registerForm").serialize();
    var url = $('#registerForm #id').val() != '' ? '/admin/article/modify' : '/register1';
    tale1.post({
        url:'/register1',
        data:params,
        success: function (result) {
            if (result && result.success) {
                tale1.alertOk({
                    text:'注册成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/login';
                        }, 500);
                    }
                });
            } else {
                tale1.alertError(result.msg || '注册失败！');
            }
        }
    });
}