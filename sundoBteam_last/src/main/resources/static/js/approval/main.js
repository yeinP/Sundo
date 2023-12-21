let a_name = document.querySelectorAll('#a_name');

for (let t = 0; t < a_name.length; t++) {
	let name = a_name[t].textContent;
	let hidName = '';

	for (let i = 0; i < name.length; i++) {
		if (i != 0 && i != name.length - 1) {
			hidName += '*';
		} else {
			hidName += name[i];
		}
	}
	a_name[t].textContent = hidName;
}

let status = document.querySelectorAll('#status');

for (let i = 0; i < status.length; i++) {
	if (status[i].textContent === '0') {
		status[i].textContent = '접수';
	} else if (status[i].textContent === '1') {
		status[i].textContent = '검토중';
	} else if (status[i].textContent === '2'){
		status[i].textContent = '결재중';
	} else if (status[i].textContent === '3'){
		status[i].textContent = '사용 승인';
	} else if (status[i].textContent === '4'){
		status[i].textContent = '신청 반려';
	}
}

let date1 = document.getElementById('date1');
let today = new Date().toISOString().substring(0, 10);
let date2 = document.getElementById('date2');
date2.value = today;
date1.setAttribute("max", today);
date2.setAttribute("max", today);

let oneWeekAgo = new Date(today);
oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);

if(date1.value == ''){
date1.value = oneWeekAgo.toISOString().substring(0, 10);	
}

let oneMonthAgo = new Date(today);
oneMonthAgo.setDate(oneMonthAgo.getDate() - 30);

let threeMonthsAgo = new Date(today);
threeMonthsAgo.setDate(threeMonthsAgo.getDate() - 90);

let sixMonthsAgo = new Date(today);
sixMonthsAgo.setDate(sixMonthsAgo.getDate() - 180);
date1.setAttribute("min", sixMonthsAgo.toISOString().substring(0, 10));

$("#period").click(function() {
	let period = parseInt($(this).val()); 
	
	if(period === 7){
		date1.valueAsDate = oneWeekAgo;
	} else if (period === 30){
		date1.valueAsDate = oneMonthAgo;
	} else if (period === 90){
		date1.valueAsDate = threeMonthsAgo;
	} else if (period === 180){
		date1.valueAsDate = sixMonthsAgo;
	}
});

function usageCheck(){
	event.preventDefault();
    let width = 900;
    let height = 700;
    let left = (screen.width - width) / 2;
    let top = (screen.height - height) / 2;
    let win = window.open("/approval/usage", "재고 조회", "width=" + width + ",height=" + height + "," +
                "left=" + left + ",top=" + top + ",resizable=no");
}