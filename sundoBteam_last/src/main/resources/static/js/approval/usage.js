document.addEventListener("DOMContentLoaded", function() {
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

	$('#river').on('change', function() {
		let r_code = $('#river').val();
		if (r_code === 999) {
			event.preventDefault();
		} else {
			$.ajax({
				method: 'POST',
				url: `/approval/usage`,
				data: { "r_code": r_code },
				dataType: "json",
				success: function(map) {
					if (map.success) {
						let table = "<table border = \"1\">";
						table += "<tr><th>승인 번호</th><th>취수 위치</th><th>사용 용도</th><th>사용자</th><th>연락처</th><th>시작일</th><th>종료일</th><th>사용량 (㎥/일)</th></tr>";
						for (let i = 0; i < map.list.length; i++) {
							let item = map.list[i];
							console.log(item);
							table += "<tr>";
							table += "<td><a href='/approval/read?a_no=" + item.a_no + "'>" + item.a_no + "</a></td>";
							table += "<td>" + item.a_place + "</td>";
							table += "<td>" + item.a_purpose + "</td>";
							table += "<td>" + item.a_name + "</td>";
							table += "<td>" + item.a_phone + "</td>";
							table += "<td>" + item.startDate + "</td>";
							table += "<td>" + item.endDate + "</td>";
							table += "<td>" + item.a_goal + "</td>";
							table += "</tr>";
						}
						table += "</table>";
						$("#result").html(table);
					} else {
						alert("조회된 데이터가 없습니다.");
					}
				}
			});
		}
	});
});