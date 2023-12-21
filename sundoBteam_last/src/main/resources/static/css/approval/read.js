$(document).ready(function() {
	$(".download_C").click(function(event) {
		event.preventDefault();
		let fno = $(this).prev(".fno").val();
		window.location.href = "/complaint/downloadFile_C?fno=" + fno;
	});
});

$(document).ready(function() {
	$(".download_R").click(function(event) {
		event.preventDefault();
		let fno = $(this).prev(".fno").val();
		window.location.href = "/complaint/downloadFile_R?fno=" + fno;
	});
});

let cuname = document.getElementById('cuname').textContent;
let hidName = '';

for (let i = 0; i < cuname.length; i++) {
	if (i != 0 && i != cuname.length - 1) {
		hidName += '*';
	} else {
		hidName += cuname[i];
	}
	document.getElementById('cuname').textContent = hidName;
}

let statusNum = document.querySelector('#status').textContent;
let status = document.getElementById('status');

if (statusNum === '0') {
	status.textContent = '접수';
} else if (statusNum === '1') {
	status.textContent = '답변 완료';
} else {
	status.textContent = '처리 완료'
}

function reviseCheck() {
	let cno = document.getElementById('cno').textContent;
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
			if (check.value == '') {
				alert('비밀번호를 입력해주세요.');
			} else if (check !== pword) {
				alert('비밀번호가 일치하지 않습니다.');
			} else {
				window.location.href = '/complaint/revise?cno=' + cno;
			}
		}
	});
}

function deleteCheck() {
	let cno = document.getElementById('cno').textContent;
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
			if (check.value == '') {
				alert('비밀번호를 입력해주세요.');
			} else if (check !== pword) {
				alert('비밀번호가 일치하지 않습니다.');
			} else {
				window.location.href = '/complaint/delete?cno=' + cno;
			}
		}
	});
}

function reply() {
	let cno = document.getElementById('cno').textContent;
	let reply = confirm('답변을 등록하시겠습니까?');
	if (reply) {
		window.location.href = '/complaint/reply/?cno=' + cno;
	}
}

function solved() {
	let cno = document.getElementById('cno').textContent;
	let end = confirm('민원 처리를 완료 하시겠습니까?');
	if (end) {
		window.location.href = '/complaint/solved/?cno=' + cno;
	}
}