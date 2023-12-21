let c_loc = document.getElementById('c_loc').value;
let loca = c_loc.split(' ');

let loc1 = loca[0];
let loc2 = loca[1];

let select = document.getElementById('loc1');

let bigSi = ['서울특별시', '경기도', '인천광역시']

for (let i = 0; i < bigSi.length; i++) {
	let option = document.createElement('option');
	option.value = bigSi[i];
	option.textContent = bigSi[i];

	if (bigSi[i] == loc1) {
		option.selected = true;
	}

	select.appendChild(option);
}

let select2 = document.getElementById('loc2');

let si = ['고양시', '과천시', '광명시', '광주시', '구리시', '군포시', '김포시', '남양주시', '동두천시',
	'부천시', '성남시', '수원시', '시흥시', '안산시', '안성시', '안양시',
	'양주시', '여주시', '오산시', '용인시', '의왕시', '의정부시', '이천시',
	'파주시', '평택시', '포천시', '하남시', '화성시']

for (let i = 0; i < si.length; i++) {
	let option = document.createElement('option');
	option.value = si[i];
	option.textContent = si[i];

	if (si[i] == loc2) {
		option.selected = true;
		select2.disabled = false;
	}
	select2.appendChild(option);
}

$("#loc1").on('change', function() {
	if ($("#loc1").val() == '경기도') {
		$("#loc2").prop('disabled', false);
	} else {
		$("#loc2").prop('disabled', true);
		$("#loc2").val('999');
	}
});

let revise = document.querySelector('#revise');
revise.addEventListener('click', function(event) {
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
	} else {
		if (confirm('민원을 수정합니다. 계속하시겠습니까?')) {
			if (loc2.value == '999') {
				document.getElementById('hidLoc').value = loc1.value;
			} else {
				document.getElementById('hidLoc').value = loc1.value + ' ' + loc2.value;
			}
		} else {
			event.preventDefault();
		}
	}
});
