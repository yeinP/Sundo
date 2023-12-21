let wlobscd = [];
let obsnm = [];
let agcnm = [];
let gdt = [];
let searchType = "1H";

let metro = [1018640, 1018695, 1018655, 1018658, 1018698, 1018692, 1018697, 1018670, 1018680, 1018675, 1018669, 1018662, 1018683,
	1013655, 1015620, 1015645, 1015644, 1007633, 1007634, 1019667, 1018693,
	1016650, 1016695, 1016670, 1016660, 1019675, 1018625, 1018620, 1015680,
	1018623, 1018638, 1018630, 1018635, 1018610, 1022668, 1019620, 1019630,
	1018650, 1101620, 1101610, 1007604, 1018690, 1007690, 1007697, 1007685,
	1007680, 1007639, 1007641, 1007620, 1007625, 1007617, 1007655, 1007635,
	1007615, 1007640, 1007650, 1022680, 1023662, 1023640, 1022670, 1021680,
	1022662, 1021650, 1101645, 1016607, 1018665, 1007642, 1007645, 1007605,
	1007662, 1007664, 1019680, 1023674, 1023660, 1023680, 1023670, 1101635,
	1101670, 1101663, 1101680, 1101665, 1022646, 1022655, 1022650, 1022640,
	1022648, 1022647, 1101650, 1101605, 1201620];
let seoul = [1018640, 1018695, 1018655, 1018658, 1018698, 1018692, 1018697, 1018670, 1018680, 1018675, 1018669, 1018662, 1018683];
let gyeonggi = [1013655, 1015620, 1015645, 1015644, 1007633, 1007634, 1019667, 1018693,
	1016650, 1016695, 1016670, 1016660, 1019675, 1018625, 1018620, 1015680,
	1018623, 1018638, 1018630, 1018635, 1018610, 1022668, 1019620, 1019630,
	1018650, 1101620, 1101610, 1007604, 1018690, 1007690, 1007697, 1007685,
	1007680, 1007639, 1007641, 1007620, 1007625, 1007617, 1007655, 1007635,
	1007615, 1007640, 1007650, 1022680, 1023662, 1023640, 1022670, 1021680,
	1022662, 1021650, 1101645, 1016607, 1018665, 1007642, 1007645, 1007605,
	1007662, 1007664, 1019680, 1023674, 1023660, 1023680, 1023670, 1101635,
	1101670, 1101663, 1101680, 1101665, 1022646, 1022655, 1022650, 1022640,
	1022648, 1022647, 1101650, 1101605];
let incheon = [1201620];

let apiUrl2 = "http://api.hrfco.go.kr/89D58E93-6886-47F5-B9F4-A965F0D3FD60/waterlevel/info.json";
let table = document.getElementById("tableTitle");
$(document).ready(function() {
	let startToday = new Date();
	startToday.setDate(startToday.getDate() - 1);
	let syear = startToday.getFullYear();
	let smonth = (startToday.getMonth() + 1).toString().padStart(2, '0');
	let sday = startToday.getDate().toString().padStart(2, '0');
	let shour = startToday.getHours();
	let shourString = shour.toString().padStart(2, '0');
	let startD = syear + smonth + sday + shourString;

	let endToday = new Date();
	let eyear = endToday.getFullYear();
	let emonth = (endToday.getMonth() + 1).toString().padStart(2, '0');
	let eday = endToday.getDate().toString().padStart(2, '0');
	let ehour = endToday.getHours();
	let ehourString = ehour.toString().padStart(2, '0');
	let endD = eyear + emonth + eday + ehourString;

	let now = new Date();
	let localDatetime = eyear + '-' +
		('0' + (now.getMonth() + 1)).slice(-2) + '-' +
		('0' + now.getDate()).slice(-2) + 'T' +
		('0' + now.getHours()).slice(-2) + ':' +
		('0' + now.getMinutes()).slice(-2);

	document.getElementById('startDate').value = localDatetime;

	$.getJSON(apiUrl2, function(result) {
		for (let i = 0; i < result.content.length; i++) {
			if (metro.includes(parseInt(result.content[i].wlobscd))) {
				wlobscd.push(result.content[i].wlobscd);
				obsnm.push(result.content[i].obsnm);
				agcnm.push(result.content[i].agcnm);
				gdt.push(parseFloat(result.content[i].gdt));
			}
		}
		fetchAndDisplayData(startD, endD);
	});
});

function fetchAndDisplayData(startD, endD) {
	let promises = [];
	let fDate = getCurrentDateTimeFormatted();
	for (let j = 0; j < wlobscd.length; j++) {
		let apiUrl1 = "http://api.hrfco.go.kr/89D58E93-6886-47F5-B9F4-A965F0D3FD60/waterlevel/list/" + searchType + "/" + wlobscd[j] + "/" + startD + "/" + endD + ".json";
		(function(index) {
			promises.push(new Promise(function(resolve, reject) {
				$.get(apiUrl1, function(result) {
					var waterLevel = [];
					var flowWater = [];
					for (let i = result.content.length - 1; i >= 0; i--) {
						waterLevel.push(result.content[i].wl === ' '? '-' : parseFloat(result.content[i].wl));
						flowWater.push(result.content[i].fw === ' ' || result.content[i].fw === '0.00' ? '-' : parseFloat(result.content[i].fw));
					}
					resolve({ waterLevel, flowWater, index });
				});
			}));
		})(j);
	}

	Promise.all(promises).then(function(results) {
		let rows = '';
		for (let i = 0; i < results.length; i++) {
			let row = "<tr>";
			var j = results[i].index;
			row += "<th class='f' value='" + wlobscd[j] + "'>" + obsnm[j] + "</th>";
			row += "<td>" + agcnm[j] + "</td>";

			row += "<td>" + (results[i].waterLevel[21] == '-' ? '-' : results[i].waterLevel[21].toFixed(2)) + "</td>";

			row += "<td value='" + wlobscd[j] + "'>" + (results[i].waterLevel[24] == '-' ? '-' : results[i].waterLevel[24].toFixed(2)) + "</td>";

			row += "<td>" + (results[i].flowWater[24] == '-' ? '-' : results[i].flowWater[24].toFixed(2)) + "</td>";

			row += "<td>" + (gdt[j]+results[i].waterLevel[24]).toFixed(2) + "</td>";
			row += "</tr>";
			rows += row;
		}
		$("table tbody").append(rows);
	});
}


$(".searchBtn").click(function() {
	let region = $("#region").val();
	const inputDate = document.getElementById("startDate").value;
	const endDate = inputDate.replace(/[-:T]/g, '').slice(0, -2);

	const originalDate = new Date(endDate.substring(0, 4), endDate.substring(4, 6) - 1, endDate.substring(6, 8), endDate.substring(8, 10), endDate.substring(10, 12));
	const oneDayAgo = new Date(originalDate.getTime() - 24 * 60 * 60 * 1000);

	const startDate = oneDayAgo.getFullYear().toString() +
		('0' + (oneDayAgo.getMonth() + 1)).slice(-2) +
		('0' + oneDayAgo.getDate()).slice(-2) +
		('0' + oneDayAgo.getHours()).slice(-2);
	$("table tbody").empty();

	if (region === '999') {
		fetchAndDisplayData(startDate, endDate);
	} else {
		if (region === 'seoul') {
			let r_codes = [];
			let r_names = [];
			let r_roles = [];
			let r_gdt = [];

			$.getJSON(apiUrl2, function(result) {
				for (let i = 0; i < result.content.length; i++) {
					if (seoul.includes(parseInt(result.content[i].wlobscd))) {
						r_codes.push(result.content[i].wlobscd);
						r_names.push(result.content[i].obsnm);
						r_roles.push(result.content[i].agcnm);
						r_gdt.push(result.content[i].gdt == ' ' || result.content[i].gdt == '0' ? '-' : parseFloat(result.content[i].gdt));
					}
				}
				searchAndFetch(startDate, r_codes, r_names, r_roles, r_gdt, endDate);
			});
		} else if (region === 'gyeonggi') {
			let r_codes = [];
			let r_names = [];
			let r_roles = [];
			let r_gdt = [];
			$.getJSON(apiUrl2, function(result) {
				for (let i = 0; i < result.content.length; i++) {
					if (gyeonggi.includes(parseInt(result.content[i].wlobscd))) {
						r_codes.push(result.content[i].wlobscd);
						r_names.push(result.content[i].obsnm);
						r_roles.push(result.content[i].agcnm);
						r_gdt.push(result.content[i].gdt == ' ' || result.content[i].gdt == '0' ? '-' : parseFloat(result.content[i].gdt));
					}
				}
				searchAndFetch(startDate, r_codes, r_names, r_roles, r_gdt, endDate);
			});
		} else if (region === 'incheon') {
			let r_codes = [];
			let r_names = [];
			let r_roles = [];
			let r_gdt = [];
			$.getJSON(apiUrl2, function(result) {
				for (let i = 0; i < result.content.length; i++) {
					if (incheon.includes(parseInt(result.content[i].wlobscd))) {
						r_codes.push(result.content[i].wlobscd);
						r_names.push(result.content[i].obsnm);
						r_roles.push(result.content[i].agcnm);
						r_gdt.push(result.content[i].gdt == ' ' || result.content[i].gdt == '0' ? '-' : parseFloat(result.content[i].gdt));
					}
				}
				searchAndFetch(startDate, r_codes, r_names, r_roles, r_gdt, endDate);
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

function searchAndFetch(startD, r_codes, r_names, r_roles, r_gdt, endD) {
	let promises = [];
	for (let j = 0; j < r_codes.length; j++) {
		let apiUrl1 = "http://api.hrfco.go.kr/89D58E93-6886-47F5-B9F4-A965F0D3FD60/waterlevel/list/" + searchType + "/" + r_codes[j] + "/" + startD + "/" + endD + ".json";
		(function(index) {
			promises.push(new Promise(function(resolve, reject) {
				$.get(apiUrl1, function(result) {
					var waterLevel = [];
					var flowWater = [];
					for (let i = result.content.length - 1; i >= 0; i--) {
						waterLevel.push(result.content[i].wl === ' ' || result.content[i].wl === '0.00' ? '-' : parseFloat(result.content[i].wl));
						flowWater.push(result.content[i].fw === ' ' || result.content[i].fw === '0.00' ? '-' : parseFloat(result.content[i].fw));
					}
					resolve({ waterLevel, flowWater, index });
				});
			}));
		})(j);
	}

	Promise.all(promises).then(function(results) {
		let rows = '';
		for (let i = 0; i < results.length; i++) {
			let row = "<tr>";
			var j = results[i].index;
			row += "<th class='f' value='" + r_codes[j] + "'>" + r_names[j] + "</th>";
			row += "<td>" + r_roles[j] + "</td>";

			row += "<td>" + (results[i].waterLevel[21] == '-' ? '-' : results[i].waterLevel[21].toFixed(2)) + "</td>";

			row += "<td value='" + wlobscd[j] + "'>" + (results[i].waterLevel[24] == '-' ? '-' : results[i].waterLevel[24].toFixed(2)) + "</td>";

			row += "<td>" + (results[i].flowWater[24] == '-' ? '-' : results[i].flowWater[24].toFixed(2)) + "</td>";

			row += "<td>" + (gdt[j]+results[i].waterLevel[24]).toFixed(2) + "</td>";
			row += "</tr>";
			rows += row;
		}
		$("table tbody").append(rows);
	});
}