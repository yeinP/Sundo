let rfobscd = [];
let obsnm = [];
let agcnm = [];
let searchType = "1H";

let metro = [10134020, 10154020, 10154030, 10154010, 10134030, 10184150, 10074110, 10164080,
	10164010, 10164020, 10184170, 10194010, 10184060, 10184110, 10184220, 10174020,
	10184160, 10184230, 10184130, 10184180, 10184040, 12024010, 12024020, 11014120,
	10074080, 11014130, 11014020, 10184090, 10224050, 10064030, 10074010, 10074040,
	10074070, 10074120, 10074030, 10074100, 10204010, 10204020, 10214010, 10164050,
	11014090, 10164070, 10164060, 10164040, 11014030, 11014100, 10164030, 10184210,
	10184125, 10184010, 10074060, 10074090, 10234020, 10234030, 10234040, 11014140,
	10234010, 11014080, 11014010, 11014070, 10184020, 10224040, 10224070, 10224020,
	10224010, 10184120, 10224060, 10224090, 11014060, 11014040, 10184080, 10184100,
	10184190, 10184140, 10184200];
let seoul = [10184080, 10184100, 10184190, 10184140, 10184200];
let gyeonggi = [10134020, 10154020, 10154030, 10154010, 10134030, 10184150, 10074110, 10164080,
	10164010, 10164020, 10184170, 10194010, 10184060, 10184110, 10184220, 10174020,
	10184160, 10184230, 10184130, 10184180, 10184040, 12024010, 12024020, 11014120,
	10074080, 11014130, 11014020, 10184090, 10224050, 10064030, 10074010, 10074040,
	10074070, 10074120, 10074030, 10074100, 10204010, 10204020, 10214010, 10164050,
	11014090, 10164070, 10164060, 10164040, 11014030, 11014100, 10164030, 10184210,
	10184125, 10184010, 10074060, 10074090, 10234020, 10234030, 10234040, 11014140,
	10234010, 11014080, 11014010, 11014070, 10184020, 10224040, 10224070, 10224020,
	10224010, 10184120, 10224060, 10224090, 11014060, 11014040];

let apiUrl2 = "http://api.hrfco.go.kr/89D58E93-6886-47F5-B9F4-A965F0D3FD60/rainfall/info.json";
let table = document.getElementById("tableTitle");
$(document).ready(function() {
	let startToday = new Date();
	startToday.setDate(startToday.getDate() - 1);
	let syear = startToday.getFullYear();
	let smonth = (startToday.getMonth() + 1).toString().padStart(2, '0');
	let sday = startToday.getDate().toString().padStart(2, '0');
	let sminutes = startToday.getMinutes();
	let shour = startToday.getHours();
	let shourString = shour.toString().padStart(2, '0');
	let adjustSString = sminutes.toString().padStart(2, '0');
	let startD = syear + smonth + sday + shourString + adjustSString;

	let endToday = new Date();
	let eyear = endToday.getFullYear();
	let emonth = (endToday.getMonth() + 1).toString().padStart(2, '0');
	let eday = endToday.getDate().toString().padStart(2, '0');
	let eminutes = endToday.getMinutes();
	let ehour = endToday.getHours();
	let ehourString = ehour.toString().padStart(2, '0');
	let adjustMString = eminutes.toString().padStart(2, '0');
	let endD = eyear + emonth + eday + ehourString + adjustMString;

	let now = new Date();
	let localDatetime = eyear + '-' +
		('0' + (now.getMonth() + 1)).slice(-2) + '-' +
		('0' + now.getDate()).slice(-2) + 'T' +
		('0' + now.getHours()).slice(-2) + ':' +
		('0' + now.getMinutes()).slice(-2);

	document.getElementById('startDate').value = localDatetime;

	$.getJSON(apiUrl2, function(result) {
		for (let i = 0; i < result.content.length; i++) {
			if (metro.includes(parseInt(result.content[i].rfobscd))) {
				rfobscd.push(result.content[i].rfobscd);
				obsnm.push(result.content[i].obsnm); 
				agcnm.push(result.content[i].agcnm);
			}
		}
		fetchAndDisplayData(startD, endD);
	});
});

function fetchAndDisplayData(startD, endD) {
	let promises = [];
	let fDate = getCurrentDateTimeFormatted();
	for (let j = 0; j < rfobscd.length; j++) {
		let apiUrl1 = "http://api.hrfco.go.kr/89D58E93-6886-47F5-B9F4-A965F0D3FD60/rainfall/list/" + searchType + "/" + rfobscd[j] + "/" + startD + "/" + endD + ".json";
		(function(index) {
			promises.push(new Promise(function(resolve, reject) {
				$.get(apiUrl1, function(result) {
					var rainFall = [];
					for (let i = result.content.length - 1; i >= 0; i--) {
						rainFall.push(parseFloat(result.content[i].rf));
					}
					resolve({ rainFall, index });
				});
			}));
		})(j);
	}

	Promise.all(promises).then(function(results) {
		let rows = '';
		for (let i = 0; i < results.length; i++) {
			let row = "<tr>";
			var j = results[i].index;
			row += "<th value='" + rfobscd[j] + "'>" + obsnm[j] + "</th>";
			row += "<td>" + agcnm[j] + "</td>";

			row += "<td value='" + rfobscd[j] + "'>" + (fDate < parseInt(endD)? '-' : results[i].rainFall[24].toFixed(1)) + "</td>";

			let three = 0;
			for (let k = 0; k < 3; k++) {
				three += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (fDate < parseInt(endD)? '-' : three.toFixed(1)) + "</td>";

			let six = 0;
			for (let k = 0; k < 6; k++) {
				six += parseFloat(fDate < parseInt(endD)? '-' : results[i].rainFall[24 - k]);
			}
			row += "<td>" + (fDate < parseInt(endD)? '-' : six.toFixed(1)) + "</td>";

			let twelve = 0;
			for (let k = 0; k < 12; k++) {
				twelve += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (fDate < parseInt(endD)? '-' : twelve.toFixed(1)) + "</td>";

			let twentyfour = 0;
			for (let k = 0; k < 24; k++) {
				twentyfour += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (fDate < parseInt(endD)? '-' : twentyfour.toFixed(1)) + "</td>";

			row += "</tr>";
			rows += row;
		}
		$("table tbody").append(rows);
	});
}


$(".searchBtn").click(function() {
	let region = $("#region").val();
	const inputDate = document.getElementById("startDate").value;
	const endDate = inputDate.replace(/[-:T]/g, '');

	const originalDate = new Date(endDate.substring(0, 4), endDate.substring(4, 6) - 1, endDate.substring(6, 8), endDate.substring(8, 10), endDate.substring(10, 12));
	const oneDayAgo = new Date(originalDate.getTime() - 24 * 60 * 60 * 1000);

	const startDate = oneDayAgo.getFullYear().toString() +
		('0' + (oneDayAgo.getMonth() + 1)).slice(-2) +
		('0' + oneDayAgo.getDate()).slice(-2) +
		('0' + oneDayAgo.getHours()).slice(-2) +
		('0' + oneDayAgo.getMinutes()).slice(-2);
	$("table tbody").empty();

	if (region === '999') {
		fetchAndDisplayData(startDate, endDate);
	} else {
		if (region === 'seoul') {
			let r_codes = [];
			let r_names = [];
			let r_roles = [];

			$.getJSON(apiUrl2, function(result) {
				for (let i = 0; i < result.content.length; i++) {
					if (seoul.includes(parseInt(result.content[i].rfobscd))) {
						r_codes.push(result.content[i].rfobscd);
						r_names.push(result.content[i].obsnm);
						r_roles.push(result.content[i].agcnm); 
					}
				}
				searchAndFetch(startDate, r_codes, r_names, r_roles, endDate);
			});
		} else if(region === 'gyeonggi'){
			let r_codes = [];
			let r_names = [];
			let r_roles = [];

			$.getJSON(apiUrl2, function(result) {
				for (let i = 0; i < result.content.length; i++) {
					if (gyeonggi.includes(parseInt(result.content[i].rfobscd))) {
						r_codes.push(result.content[i].rfobscd);
						r_names.push(result.content[i].obsnm);
						r_roles.push(result.content[i].agcnm);
					}
				}
				searchAndFetch(startDate, r_codes, r_names, r_roles, endDate);
			});
		}
	}
});

function getCurrentDateTimeFormatted() {
  const now = new Date();

  const year = now.getFullYear();
  const month = (now.getMonth() + 1).toString().padStart(2, '0');
  const day = now.getDate().toString().padStart(2, '0');
  const hours = now.getHours().toString().padStart(2, '0');
  const minutes = now.getMinutes().toString().padStart(2, '0');

  const formattedDateTime = `${year}${month}${day}${hours}${minutes}`;

  return formattedDateTime;
}

function searchAndFetch(startD, r_codes, r_names, r_roles, endD) {
	let promises = [];
	let sDate = getCurrentDateTimeFormatted();
	
	for (let j = 0; j < r_codes.length; j++) {
		let apiUrl1 = "http://api.hrfco.go.kr/89D58E93-6886-47F5-B9F4-A965F0D3FD60/rainfall/list/" + searchType + "/" + r_codes[j] + "/" + startD + "/" + endD + ".json";
		(function(index) {
			promises.push(new Promise(function(resolve, reject) {
				$.get(apiUrl1, function(result) {
					var rainFall = [];
					for (let i = result.content.length - 1; i >= 0; i--) {
						rainFall.push(parseFloat(result.content[i].rf));
					}
					resolve({ rainFall, index });
				});
			}));
		})(j);
	}

	Promise.all(promises).then(function(results) {
		let rows = '';
		for (let i = 0; i < results.length; i++) {
			let row = "<tr>";
			var j = results[i].index;
			row += "<th value='" + r_codes[j] + "'>" + r_names[j] + "</th>";
			row += "<td>" + r_roles[j] + "</td>";

			row += "<td value='" + r_codes[j] + "'>" + (sDate < parseInt(endD)? '-' : results[i].rainFall[24].toFixed(1)) + "</td>";

			let three = 0;
			for (let k = 0; k < 3; k++) {
				three += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (sDate < parseInt(endD)? '-' : three.toFixed(1)) + "</td>";

			let six = 0;
			for (let k = 0; k < 6; k++) {
				six += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (sDate < parseInt(endD)? '-' : six.toFixed(1)) + "</td>";

			let twelve = 0;
			for (let k = 0; k < 12; k++) {
				twelve += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (sDate < parseInt(endD)? '-' : twelve.toFixed(1)) + "</td>";

			let twentyfour = 0;
			for (let k = 0; k < 24; k++) {
				twentyfour += parseFloat(results[i].rainFall[24 - k]);
			}
			row += "<td>" + (sDate < parseInt(endD)? '-' : twentyfour.toFixed(1)) + "</td>";

			row += "</tr>";
			rows += row;
		}
		$("table tbody").append(rows);
	});
}