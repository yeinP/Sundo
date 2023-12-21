let cuname = document.querySelectorAll('#cuname');

for (let t = 0; t < cuname.length; t++) {
	let name = cuname[t].textContent;
	let hidName = '';

	for (let i = 0; i < name.length; i++) {
		if (i != 0 && i != name.length - 1) {
			hidName += '*';
		} else {
			hidName += name[i];
		}
	}
	cuname[t].textContent = hidName;
}

let status = document.querySelectorAll('#status');

for (let i = 0; i < status.length; i++) {
	if (status[i].textContent === '0') {
		status[i].textContent = '접수';
	} else if (status[i].textContent === '1') {
		status[i].textContent = '답변 완료';
	} else {
		status[i].textContent = '처리 완료'
	}
}

function checkPw(aaa) {
	let loggedIn = aaa.getAttribute('data-loggedIn');
	let cno = aaa.getAttribute('data-cno');
	let secret = aaa.getAttribute('data-secret');

	if (loggedIn == null && secret == 1) {
		var pword = '';
		$.ajax({
			method: 'POST',
			url: `/complaint/check`,
			data: { "cno": cno },
			dataType: "json",
			success: function(map) {
				if (map.success) {
					pword = map.pword;
				} else {
					alert('오류가 발생했습니다. 다시 시도해주세요.');
				}
				let check = prompt('비밀번호를 입력하세요.');
				if (check !== null) {
					if (check === '') {
						alert('비밀번호를 입력해주세요.');
					} else if (check !== pword) {
						alert('비밀번호가 일치하지 않습니다.');
					} else {
						window.location.href = '/complaint/read?cno=' + cno;
					}
				}
			}
		});
	} else {
		window.location.href = '/complaint/read?cno=' + cno;
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
