
var tale2 = new $.tale();
function subOrder(status) {
    var name = $('#orderForm input[name=name]').val();
    var sex = $('#orderForm input[name=sex]').val();
    var mobile = $('#orderForm input[name=mobile]').val();
    if (name == '') {
        tale1.alertWarn('用户名不能为空！');
        return;
    }
    if (sex=='') {
        tale1.alertWarn('请填写性别！');
        return;
    }
    if (mobile == '') {
        tale1.alertWarn('请输入电话号码！');
        return;
    }
    var params = $("#orderForm").serialize();
    var url = $('#orderForm #id').val() != '' ? '/admin/article/modify' : '/register1';
    tale2.post({
        url:'/register1',
        data:params,
        success: function (result) {
            if (result && result.success) {
                tale2.alertOk({
                    //text:'注册成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/login';
                        }, 500);
                    }
                });
            } else {
                tale2.alertError(result.msg || '注册失败！');
            }
        }
    });
}