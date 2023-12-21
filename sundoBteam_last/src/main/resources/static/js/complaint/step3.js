
let select = document.getElementById('loc2');

let si = ['고양시', '과천시', '광명시', '광주시', '구리시', '군포시', '김포시', '남양주시', '동두천시',
	'부천시', '성남시', '수원시', '시흥시', '안산시', '안성시', '안양시',
	'양주시', '여주시', '오산시', '용인시', '의왕시', '의정부시', '이천시',
	'파주시', '평택시', '포천시', '하남시', '화성시']

for (let i = 0; i < si.length; i++) {
	let option = document.createElement('option');
	option.value = si[i];
	option.textContent = si[i];
	select.appendChild(option);
}

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

$("#loc1").on('change', function() {
	if ($("#loc1").val() == '경기도') {
		$("#loc2").prop('disabled', false);
	} else {
		$("#loc2").prop('disabled', true);
		$("#loc2").val('999');
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


let c_submit = document.querySelector('#c_submit');
c_submit.addEventListener('click', function(event) {
	const loc1 = document.getElementById('loc1');
	const loc2 = document.getElementById('loc2');

	if (loc1.value == '999') {
		event.preventDefault();
		alert('주소지를 선택해주세요.');
	} else if (loc1.value == 'Gyeonggi-do' && loc2.value == '999') {
		event.preventDefault();
		alert('소분류를 선택해주세요.');
	} else if (document.getElementById('ctitle').value === '') {
		event.preventDefault();
		alert('제목을 입력해주세요.');
	} else if (document.getElementById('c_content').value === '') {
		event.preventDefault();
		alert('내용을 입력해주세요.');
	} else if (document.getElementById("pword").value.length < 4) {
		event.preventDefault();
		alert('비밀번호를 입력해주세요.');
	} else {
		if (confirm('민원을 등록합니다. 계속하시겠습니까?')) {
			if (loc2.value == '999') {
				document.getElementById('hidLoc').value = loc1.value;
			} else {
				document.getElementById('hidLoc').value = loc1.value + ' ' + loc2.value;
			}
			if (document.getElementById("inputCheck").checked) {
				document.getElementById("inputCheck_hidden").value = '1';
			} else {
				document.getElementById("inputCheck_hidden").value = '0';
			}
		} else {
			event.preventDefault();
		}
	}
});
