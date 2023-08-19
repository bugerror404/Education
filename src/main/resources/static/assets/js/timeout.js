var interval;
window.onload = function() {
	interval = window.setInterval("changeMinute()", 60000);
};
function changeMinute() {
	var minute = document.getElementById("minute");
	var svalue = minute.innerHTML;
	var msg;
	svalue = svalue - 1;

	if (svalue == -1) {
		window.clearInterval(interval);
		msg1=confirm("确定要取消订单吗");
		if (msg1==true){
			alert("取消成功");
			window.location.href = '/order/updateOrder';
		}
		else {
			alert("重新进行支付");
			location.reload();

		}
		return;
	}
	minute.innerHTML = svalue;
}
