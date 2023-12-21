document.addEventListener("DOMContentLoaded", function() {
	var signNum = '';
	document.getElementById("send").addEventListener("click", function(event) {
		let phonePattern = /^010\d{8}$/;
		let phoneValue = document.getElementById("p1").value + document.getElementById("p2").value
			+ document.getElementById("p3").value;
		if (phoneValue === '') {
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
			url: `/complaint/send`,
			data: { "phone": phoneValue },
			dataType: "json",
			success: function(res) {
				if (res.success) {
					signNum = res.signNum;
					alert("인증번호가 전송되었습니다.");
				} else {
					alert("전송이 실패했습니다. 다시 시도하세요.");
				}
			}
		});
	});

	document.getElementById("check").addEventListener("click", function(event) {
		let signNumInput = document.getElementById("signNum").value;
		let phone = document.getElementById("p1").value + document.getElementById("p2").value
			+ document.getElementById("p3").value;
		if (signNumInput === '') {
			alert("인증번호를 입력해주세요.");
			return;
		}

		$.ajax({
			method: 'POST',
			url: `/complaint/result`,
			data: { "signNumInput": signNumInput, "signNum": signNum },
			dataType: "json",
			success: function(res) {
				if (!res) {
					event.preventDefault();
					alert("인증번호가 틀렸습니다.");
				} else {
					event.preventDefault();
					alert("인증에 성공하였습니다.");
					document.getElementById("hidPhone").value = phone;
					document.querySelector('#nextBtn').style.display = "block";
				}
			}
		});
	});
});