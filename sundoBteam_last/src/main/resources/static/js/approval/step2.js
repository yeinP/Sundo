let date1 = document.getElementById('date1');
let date2 = document.getElementById('date2');
let today = new Date().toISOString().substring(0, 10);
let oneDayLater = new Date(today);
oneDayLater.setDate(oneDayLater.getDate() + 1);

date1.value = oneDayLater;
date2.value = oneDayLater;

date1.setAttribute("min", oneDayLater);
date2.setAttribute("min", oneDayLater);

$("#file").on('change', function() {
	let files = $("#file")[0].files;
	let fileNames = [];

	for (let i = 0; i < files.length; i++) {
		fileNames.push(files[i].name);
	}

	if (fileNames.length > 1) {
		let remainingCount = fileNames.length - 1;
		$(".upload-name").val(fileNames[0] + " 외 " + remainingCount + "건");
	} else {
		$(".upload-name").val(fileNames[0]);
	}
});

$("#purpose").on('change', function() {
	if ($("#purpose").val() == '기타') {
		$("#etc").prop('disabled', false);
	} else {
		$("#etc").prop('disabled', true);
	}
});

$("#pword").on('keyup', function() {
	const pwordPattern = /^[0-9]{1}$/;
	let keycode = event.which || event.keyCode;
	if (keycode != 8 && !pwordPattern.test(String.fromCharCode(keycode))) {
		alert('숫자만 입력할 수 있습니다.');
		$("#pword").val('');
	};
});

function formatPhoneNumber(phone) {
	return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
}

let phone = document.getElementById('hidPhone').value;

let phoneInput = document.getElementById('phoneInput');
phoneInput.value = formatPhoneNumber(phone);

let step2Btn = document.querySelector('#step2Btn');
step2Btn.addEventListener('click', function(event) {
	let river = document.getElementById('river');
	let purpose = document.getElementById('purpose');
	let detailAddress = document.getElementById('detailAddress');
	let etc = document.getElementById('etc');
	let hidPurpose = document.getElementById('hidPurpose');
	
	if (purpose.value == '기타') {
		hidPurpose.value =  '기타 (' + etc.value + ')';
		} else {hidPurpose.value = purpose.value;
	 }
	if (document.getElementById('a_name').value == '') {
		event.preventDefault();
		alert('신청자를 입력해주세요.');
	} else if (river.value == '999') {
		event.preventDefault();
		alert('취수 위치를 선택해주세요.');
	} else if (river.value != '999' && detailAddress.value == '') {
		event.preventDefault();
		alert('상세 위치를 입력하세요.');
	} else if (purpose.value == '999') {
		event.preventDefault();
		alert('용수 사용 용도를 선택하세요.');
	} else if (purpose.value == '기타' && etc.value == '') {
		event.preventDefault();
		alert('신청 사유를 입력하세요.');
	} else if (document.getElementById('a_volume').value === '') {
		event.preventDefault();
		alert('시설 용량을 입력하세요.');
	} else if (document.getElementById("a_goal").value == '') {
		event.preventDefault();
		alert('신청량을 입력하세요.');
	} else {
		if (confirm('용수 사용 신청서를 등록합니다. 계속하시겠습니까?')) {
			alert('신청이 완료되었습니다.');
		} else {
			event.preventDefault();
		}
	}
});