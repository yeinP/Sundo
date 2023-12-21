document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("send").addEventListener("click", function(event) {
		let phonePattern = /^010\d{8}$/;
		let phoneValue = document.getElementById("p1").value + document.getElementById("p2").value
			+ document.getElementById("p3").value;
		let name = document.getElementById("name").value;
		document.getElementById("hidPhone").value = phoneValue;
		document.getElementById("hidName").value = name;
		if(name.value === ''){
			event.preventDefault();
			alert("성함을 입력하세요.");
			return;
		}
		else if (phoneValue === '') {
			event.preventDefault();
			alert("휴대폰 번호를 입력해주세요.");
			return;
		}
		else if (!phonePattern.test(phoneValue)) {
			event.preventDefault();
			alert("입력하신 핸드폰 번호가 양식에 맞지 않습니다.");
			return;
		}
		$.ajax({
			method: 'POST',
			url: `/approval/myApproval/check`,
			data: { "a_phone": phoneValue, "a_name" : name},
			dataType: "json",
			success: function(success) {
				if (!success) {
					event.preventDefault();
					alert('조회 결과가 없습니다.');
					history.back();
				}
			}
		});
	});
});