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