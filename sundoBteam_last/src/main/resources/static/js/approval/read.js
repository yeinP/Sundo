$(document).ready(function() {
	$(".download_A").click(function(event) {
		event.preventDefault();
		let fno = $(this).prev(".fno").val();
		window.location.href = "/approval/downloadFile_A?fno=" + fno;
	});
});

$("#decline").on('change', function() {
    if ($(this).is(':checked')) {
        $("#a_comment").css('display', 'block');
    } else {
        $("#a_comment").css('display', 'none');
    }
});

$("#accept").on('change', function() {
    if ($(this).is(':checked')) {
        $("#a_comment").css('display', 'none');
    } else {
        $("#a_comment").css('display', 'block');
    }
});

let a_name = document.getElementById('a_name').textContent;
let hidName = '';

for (let i = 0; i < a_name.length; i++) {
	if (i != 0 && i != a_name.length - 1) {
		hidName += '*';
	} else {
		hidName += a_name[i];
	}
	document.getElementById('a_name').textContent = hidName;
}

let statusNum = document.querySelector('#status').textContent;
let status = document.getElementById('status');

if (statusNum === '0') {
	status.textContent = '접수';
} else if (statusNum === '1') {
	status.textContent = '검토중';
} else if (statusNum === '2') {
	status.textContent = '결재중';
} else if (statusNum === '3') {
	status.textContent = '승인';
} else {
	status.textContent = '반려';
}

$('#approve').on('click', function() {
	if($("#decline").is(':checked') && $('#a_comment').val() == ''){
		alert('반려 사유를 입력하세요.');
		event.preventDefault();
		$('#decline').prop('checked',true);
	}else {
		if(confirm('결재를 완료합니다.\n계속하시겠습니까?')){
			alert('결재가 완료되었습니다.');
		} else {
			event.preventDefault();
		}
		
	}
});