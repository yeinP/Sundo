//지도 띄우기
var cctvMarkers = [];
var riverMarkers = [];
var removeNonBaseLayers = [];
var pop = null;
vw.ol3.CameraPosition.center = [14134718.970064852, 4512139.392021515];
vw.ol3.CameraPosition.zoom = 9;
vw.ol3.MapOptions = {
	basemapType: vw.ol3.BasemapType.GRAPHIC,
	controlDensity: vw.ol3.DensityType.EMPTY,
	interactionDensity: vw.ol3.DensityType.FULL,
	controlsAutoArrange: true,
	homePosition: vw.ol3.CameraPosition,
	initPosition: vw.ol3.CameraPosition,
	size: [800, 800],
	isBaseLayer: true
};
function toggleMode(mapType, isChecked, checkboxId) {
	if (isChecked) {
		uncheckOtherCheckboxes(checkboxId);
		setMode(mapType);
	} else {
		setMode(vw.ol3.BasemapType.GRAPHIC);
	}
}

function uncheckOtherCheckboxes(selectedCheckboxId) {
	var checkboxes = document.querySelectorAll('input[name="mapType"]');
	checkboxes.forEach(function(checkbox) {
		if (checkbox.id !== selectedCheckboxId) {
			checkbox.checked = false;
		}
	});
}


var vmap = new vw.ol3.Map("vmap", vw.ol3.MapOptions);

var options = {
	map: vmap
	, site: vw.ol3.SiteAlignType.TOP_RIGHT  //"top-left"
	, vertical: true
	, collapsed: false
	, collapsible: false
};


//
// var myCustomButton = new vw.ol3.button.CustomButton(vmap, {
//     clickCallback: function () {
//         var view = vmap.getView();
//         view.setCenter([14134718.970064852, 4512139.392021515]);
//         view.setZoom(9);
//     },
// });

var _toolBtnList = [
	new vw.ol3.button.Init(vmap),
	new vw.ol3.button.ZoomIn(vmap),
	new vw.ol3.button.ZoomOut(vmap),
	new vw.ol3.button.DragZoomIn(vmap),
	new vw.ol3.button.DragZoomOut(vmap),
	new vw.ol3.button.Pan(vmap),
	new vw.ol3.button.Prev(vmap),
	new vw.ol3.button.Next(vmap),

	// new vw.ol3.button.Full(vmap),
	new vw.ol3.button.Distance(vmap),
	new vw.ol3.button.Area(vmap)
];

var toolBar = new vw.ol3.control.Toolbar(options);
toolBar.addToolButtons(_toolBtnList);
vmap.addControl(toolBar);

// var vmap = new vw.ol3.Map("vmap", vw.ol3.MapOptions);
markerLayer = new vw.ol3.layer.Marker(vmap);
vmap.addLayer(markerLayer);

function setMode(basemapType) {
	vmap.setBasemapType(basemapType);
}

var popupWindow;

function openWindowPop(url, title) {
	if (popupWindow && !popupWindow.closed) {
		popupWindow.close();
	}
	const left = (window.screen.width - 400) / 2;
	const top = (window.screen.height - 400) / 2;
	popupWindow = window.open(
		url,
		title,
		`width=950, height=480, top=${top}, left=${left}`);

	if (window.focus) {
		popupWindow.focus();
	}
}

var GSLine = new ol.layer.Image({
	source: new ol.source.ImageWMS({
		url: 'http://localhost:8080/geoserver/sundo/wms',
		params: {
			'LAYERS': 'sundo:line33',
			'FORMAT': 'image/png',
			// 'cql_filter': 'bjcd=4100000000'

		},
	}),
});
removeNonBaseLayers.push(GSLine);
vmap.addLayer(GSLine)
// vmap.on("click", function (evt) {
//     var coordinate = evt.coordinate;
//     var viewResolution = vmap.getView().getResolution();
//     var wmsSource = GSLine.getSource();
//     var url = wmsSource.getGetFeatureInfoUrl(
//         coordinate, viewResolution, 'EPSG:3857',
//         {'INFO_FORMAT': 'application/json', 'FEATURE_COUNT': 1, 'QUERY_LAYERS': 'gis:line33'}
//     );
//
//     if (url) {
//         $.getJSON(url, function (response) {
//             if (response.features.length > 0) {
//                 var feature = response.features[0];
//                 console.log('Feature 정보:', feature.properties.bjcd);
//
//                 var GSLineLayer = new ol.layer.Image({
//                     source: new ol.source.ImageWMS({
//                         url: 'http://localhost:9898/geoserver/gis/wms',
//                         params: {
//                             'LAYERS': 'gis:line33',
//                             'FORMAT': 'image/png',
//                             'cql_filter': 'bjcd=' + feature.properties.bjcd
//                         },
//                     }),
//                 });
//
//                 if (removeNonBaseLayers.length !== 0) {
//                     removeNonBaseLayers.forEach(function (layer) {
//                         vmap.removeLayer(layer);
//                     });
//                 }
//                 removeNonBaseLayers.push(GSLineLayer);
//                 vmap.addLayer(GSLineLayer);
//
//                 var hdms = escapeHtml(feature.properties.name); // XSS 방지를 위한 HTML 이스케이프 처리
//                 if (!pop) {
//                     pop = new vw.ol3.popup.Popup();
//                 }
//                 vmap.addOverlay(pop);
//                 pop.show('<div onclick="viewClick(\'' + feature.properties.name + '\', [' + coordinate.join(', ') + '])">' + hdms + '</div>', coordinate);
//
//             } else {
//                 console.log('Feature를 찾을 수 없음');
//             }
//         }).fail(function () {
//             console.error("GetFeatureInfo 요청 실패");
//         });
//     }
// });




function escapeHtml(text) {
	return text
		.replace(/&/g, "&amp;")
		.replace(/</g, "&lt;")
		.replace(/>/g, "&gt;")
		.replace(/"/g, "&quot;")
		.replace(/'/g, "&#039;");
}

var seoulRiver;
document.getElementById('seoul_river').addEventListener('change', function() {
	if (!seoulRiver) {
		seoulRiver = new ol.layer.Image({
			source: new ol.source.ImageWMS({
				url: 'http://localhost:8080/geoserver/sundo/wms',
				params: {
					'LAYERS': 'sundo:lsmd_cont_uj201_11_202310',
					'FORMAT': 'image/png',

				},
			}),
		});

		vmap.addLayer(seoulRiver);
	} else {
		vmap.removeLayer(seoulRiver);
		seoulRiver = undefined;
	}
});
var gyeongiRiver;
document.getElementById('gyeonggi_river').addEventListener('change', function() {
	if (!gyeongiRiver) {
		gyeongiRiver = new ol.layer.Image({
			source: new ol.source.ImageWMS({
				url: 'http://localhost:8080/geoserver/sundo/wms',
				params: {
					'LAYERS': 'sundo:lsmd_cont_uj201_41_202310',
					'FORMAT': 'image/png',

				},
			}),
		});

		vmap.addLayer(gyeongiRiver);
	} else {
		vmap.removeLayer(gyeongiRiver);
		gyeongiRiver = undefined;
	}
});
var BaseBasin;
document.getElementById('BaseBasin').addEventListener('change', function() {
	console.log('111')
	if (!BaseBasin) {
		BaseBasin = new ol.layer.Image({
			source: new ol.source.ImageWMS({
				url: 'http://localhost:8080/geoserver/sundo/wms',
				params: {
					'LAYERS': 'sundo:wkmbbsn',
					'FORMAT': 'image/png',

				},
			}),
		});

		vmap.addLayer(BaseBasin);
	} else {
		vmap.removeLayer(BaseBasin);
		BaseBasin = undefined;
	}
});
var MiddleBasin;
document.getElementById('MiddleBasin').addEventListener('change', function() {
	console.log('111')
	if (!MiddleBasin) {
		MiddleBasin = new ol.layer.Image({
			source: new ol.source.ImageWMS({
				url: 'http://localhost:8080/geoserver/sundo/wms',
				params: {
					'LAYERS': 'sundo:wkmmbsn',
					'FORMAT': 'image/png',

				},
			}),
		});

		vmap.addLayer(MiddleBasin);
	} else {
		vmap.removeLayer(MiddleBasin);
		MiddleBasin = undefined;
	}
});


var SmallBasin;
document.getElementById('SmallBasin').addEventListener('change', function() {
	console.log('111')
	if (!SmallBasin) {
		SmallBasin = new ol.layer.Image({
			source: new ol.source.ImageWMS({
				url: 'http://localhost:8080/geoserver/sundo/wms',
				params: {
					'LAYERS': 'sundo:wkmsbsn',
					'FORMAT': 'image/png',

				},
			}),
		});

		vmap.addLayer(SmallBasin);
	} else {
		vmap.removeLayer(SmallBasin);
		SmallBasin = undefined;
	}
});
//
// var seouldrainage;
// document.getElementById('seoul_drainage').addEventListener('click', function () {
//     if (!seouldrainage) {
//         seouldrainage = new ol.layer.Image({
//             source: new ol.source.ImageWMS({
//                 url: 'http://localhost:9898/geoserver/gis/wms',
//                 params: {
//                     'LAYERS': 'sundo:lsmd_cont_um730_11_202310',
//                     'FORMAT': 'image/png',
//
//                 },
//             }),
//         });
//
//         vmap.addLayer(seouldrainage);
//     } else {
//         vmap.removeLayer(seouldrainage);
//         seouldrainage = undefined;
//     }
// });
//
// var gyeonggidrainage;
// document.getElementById('gyeonggi_drainage').addEventListener('click', function () {
//     if (!gyeonggidrainage) {
//         gyeonggidrainage = new ol.layer.Image({
//             source: new ol.source.ImageWMS({
//                 url: 'http://localhost:9898/geoserver/gis/wms',
//                 params: {
//                     'LAYERS': 'sundo:lsmd_cont_um730_41_202310',
//                     'FORMAT': 'image/png',
//
//                 },
//             }),
//         });
//
//         vmap.addLayer(gyeonggidrainage);
//     } else {
//         vmap.removeLayer(gyeonggidrainage);
//         gyeonggidrainage = undefined;
//     }
// });

function addRiverMarker(code, longitude, latitude, text) {
        var markerOption = {
            x: longitude,
            y: latitude,
            epsg: "EPSG:4326",
            iconUrl: '/img/map-icon2.png',
            title: text,
            contents: '<div onclick="openWindowPop(\'/hanRiver/detail/river/' + code + '\', \'' + text + '\')">수위 정보</div><div onclick="openWindowPop(\'/River/load?id=' + code + '\', \'' + text + '\')">하천 정보</div>' ,
            attr: { "code": code, "name": text },
        };

        var marker = markerLayer.addMarker(markerOption);
        riverMarkers.push(marker);
        marker.data = {
            code: code,
            name: text
        };
    }
function addCCTVMarker(code, longitude, latitude, text) {
	var markerOption = {
		x: longitude,
		y: latitude,
		epsg: "EPSG:4326",
		iconUrl: '/img/cctv-icon2.png',
		title: text,
		// URL 경로를 'openWindowPop' 함수에 인자로 전달합니다.
		contents: '<div onclick="modalClick(\'' + code + '\',\'' + text + '\')">실시간 cctv 영상</div>',
		attr: { "code": code, "name": text },
	};
	// ... 나머지 함수 구현


	var marker = markerLayer.addMarker(markerOption);
	cctvMarkers.push(marker);
	marker.data = {
		code: code,
		name: text
	};
}

var riverCheckBox = document.getElementById("allRiver");
riverCheckBox.addEventListener("change", function() {
	if (riverCheckBox.checked) {
		// river 마커를 추가하는 로직
		$.getJSON("/HanRiverMap/marker", function(response) {
			for (var i = 0; i < response.length; i++) {
				var markerData = response[i];
				var longitude = markerData.r_longitude;
				var latitude = markerData.r_latitude;
				var text = markerData.r_name;
				var code = markerData.r_code;
				// river 마커를 추가
				addRiverMarker(code, longitude, latitude, text);
			}
		});
	} else {
		// river 마커 제거
		removeRiverMarkers();
	}
});
var cctvCheckBox = document.getElementById("addCctv");
cctvCheckBox.addEventListener("change", function() {
	// cctv 마커를 추가하는 로직
	if (cctvCheckBox.checked) {
		$.getJSON("/Addr/lat", function(response) {
			removeCCTVMarkers(); // 기존 cctv 마커 제거
			for (var i = 0; i < response.length; i++) {
				var markerData = response[i];
				var longitude = markerData.response.result.point.x;
				var latitude = markerData.response.result.point.y;
				var text = markerData.name;
				var code = markerData.code;
				// cctv 마커를 추가
				addCCTVMarker(code, longitude, latitude, text);
			}
		});
	} else {
		removeCCTVMarkers();
	}
});

function removeCCTVMarkers() {
	cctvMarkers.forEach(function(cctvMarker) {
		markerLayer.removeMarker(cctvMarker);
	});
	cctvMarkers = []; // 배열 초기화
}

function removeRiverMarkers() {
	riverMarkers.forEach(function(riverMarker) {
		markerLayer.removeMarker(riverMarker);
	});
	riverMarkers = []; // 배열 초기화
}

// 모달 열기
function openModal() {
	document.getElementById('videoModal').style.display = 'block';
}

// 모달 닫기
function closeModal() {
	document.getElementById('videoModal').style.display = 'none';
}

// 닫기 버튼에 이벤트 리스너 추가
document.getElementsByClassName('close')[0].onclick = function() {
	closeModal();
}


//
function zoomMap(lon, lat) {
	var sourceCoord = ol.proj.fromLonLat([lon, lat]);
	var targetCoord = ol.proj.transform(sourceCoord, 'EPSG:4326', 'EPSG:4326');
	var view = vmap.getView();
	view.setCenter(targetCoord);
	view.setZoom(15);
	vmap.removeLayer(rainFallMarkerLayer);
	rainFallMarkerLayer = new vw.ol3.layer.Marker(vmap);
	vmap.addLayer(rainFallMarkerLayer);
	addRainFallMarker();

	$('#riverCheckBoxList').click(function() {
		vmap.removeLayer(rainFallMarkerLayer);
		var view = vmap.getView();
		view.setCenter([14134718.970064852, 4512139.392021515]);
		view.setZoom(9);
	});
}


rainFallMarkerLayer = new vw.ol3.layer.Marker(vmap);
vmap.addLayer(rainFallMarkerLayer);
function addRainFallMarker() {
	$.getJSON("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/rainfall/info.json", function(response) {
		response.content.forEach(function(markerData) {
			var addr = markerData.addr;
			if (addr.includes("경기도") || addr.includes("서울")) {
				var rfobscd = markerData.rfobscd;
				var longitude = parseCoordinate(markerData.lon);
				var latitude = parseCoordinate(markerData.lat);
				var text = markerData.obsnm;
				RainFallMarker(rfobscd, longitude, latitude, text);
			}

		});
	});
}

function RainFallMarker(rfobscd, longitude, latitude, text) {

	var markerOption = {

		x: longitude,
		y: latitude,
		epsg: "EPSG:4326",
		title: text,
		iconUrl: '/img/rainFall2.png',
		contents: '<div onclick="openWindowPop(\'/hanRiver/detail/rainFall/' + rfobscd + '\', \'' + text + '\')">강수량</div>',

		text: {
			offsetX: 0.5,
			offsetY: 20,
			font: '12px Calibri,sans-serif',
			fill: { color: '#000' },
			stroke: { color: '#fff', width: 2 },
			stroke: { color: '#fff', width: 2 },
		},
		attr: { "code": rfobscd, "name": text },
	};
	var marker = rainFallMarkerLayer.addMarker(markerOption);

	marker.data = {
		code: rfobscd,
		name: text
	};
}

$('#rainFallBtn').click(function() {
	var isChecked = $(this).prop('checked');
	if (isChecked) {
		vmap.removeLayer(rainFallMarkerLayer);
		rainFallMarkerLayer = new vw.ol3.layer.Marker(vmap);
		vmap.addLayer(rainFallMarkerLayer);
		addRainFallMarker();
	} else {
		vmap.removeLayer(rainFallMarkerLayer);
	}
});

function parseCoordinate(coordinateString) {
	var lonParts = coordinateString.split('-');
	var lonDegrees = parseFloat(lonParts[0]);
	var lonMinutes = parseFloat(lonParts[1]);
	var lonSeconds = parseFloat(lonParts[2]);
	var lonDecimal = lonDegrees + lonMinutes / 60 + lonSeconds / 3600;
	return lonDecimal; // 부동 소수점 값으로 변환
};


waterLevelMarkerLayer = new vw.ol3.layer.Marker(vmap);
vmap.addLayer(waterLevelMarkerLayer);
function addWaterLevelMarker() {
	$.getJSON("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/waterlevel/info.json", function(response) {

		response.content.forEach(function(markerData) {
			var addr = markerData.addr;
			if (addr.includes("경기도") || addr.includes("서울")) {
				var wlobscd = markerData.wlobscd;
				var longitude = parseCoordinate(markerData.lon);
				var latitude = parseCoordinate(markerData.lat);
				var text = markerData.obsnm;
				WaterLevelMarker(wlobscd, longitude, latitude, text);
			}

		});
	});
}



function WaterLevelMarker(wlobscd, longitude, latitude, text) {

	var markerOption = {
		x: longitude,
		y: latitude,
		epsg: "EPSG:4326",
		title: text,
		iconUrl: '//map.vworld.kr/images/ol3/marker_blue.png',
		contents: '<div onclick="openWindowPop(\'/hanRiver/detail/river/' + wlobscd + '\', \'' + text + '\')">하천 정보</div> ',

		text: {
			offsetX: 0.5,
			offsetY: 20,
			font: '12px Calibri,sans-serif',
			fill: { color: '#000' },
			stroke: { color: '#fff', width: 2 },
		},
		attr: { "code": wlobscd, "name": text },
	};
	var marker = waterLevelMarkerLayer.addMarker(markerOption);

	marker.data = {
		code: wlobscd,
		name: text
	};
}

$('#waterLevelBtn').click(function() {
	var isChecked = $(this).prop('checked');
	if (isChecked) {
		vmap.removeLayer(waterLevelMarkerLayer);
		waterLevelMarkerLayer = new vw.ol3.layer.Marker(vmap);
		vmap.addLayer(waterLevelMarkerLayer);
		addWaterLevelMarker();
	} else {
		vmap.removeLayer(waterLevelMarkerLayer);
	}
});

//댐정보 띄우기
damMarkerLayer = new vw.ol3.layer.Marker(vmap);
vmap.addLayer(damMarkerLayer);
function addDamMarker() {
	$.getJSON("http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/dam/info.json", function(response) {
		response.content.forEach(function(markerData) {
			var addr = markerData.addr;
			if (addr.includes("경기도") || addr.includes("서울")) {
				var dmobscd = markerData.dmobscd;
				var longitude = parseCoordinate(markerData.lon);
				var latitude = parseCoordinate(markerData.lat);
				var text = markerData.obsnm;
				DamMarker(dmobscd, longitude, latitude, text);
			}

		});
	});
}



function DamMarker(dmobscd, longitude, latitude, text) {

	var markerOption = {
		x: longitude,
		y: latitude,
		epsg: "EPSG:4326",
		title: text,
		iconUrl: '/img/dam.png',
		contents: '<div onclick="openWindowPop(\'/hanRiver/detail/dam/' + dmobscd + '\', \'' + text + '\')">수위 정보</div> ',

		text: {
			offsetX: 0.5,
			offsetY: 20,
			font: '12px Calibri,sans-serif',
			fill: { color: '#000' },
			stroke: { color: '#fff', width: 2 },
		},
		attr: { "code": dmobscd, "name": text },
	};
	var marker = damMarkerLayer.addMarker(markerOption);

	marker.data = {
		code: dmobscd,
		name: text
	};
}

$('#damBtn').click(function() {
	var isChecked = $(this).prop('checked');
	if (isChecked) {
		vmap.removeLayer(damMarkerLayer);
		damMarkerLayer = new vw.ol3.layer.Marker(vmap);
		vmap.addLayer(damMarkerLayer);
		addDamMarker();
	} else {
		vmap.removeLayer(damMarkerLayer);
	}
});


function updateWeatherImage() {
	console.log("updateWeatherImage() excuted")
	let currentDate = new Date();
	let year = currentDate.getFullYear();
	let month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	let day = currentDate.getDate().toString().padStart(2, '0');
	let hours = currentDate.getHours().toString().padStart(2, '0');
	let minutes = currentDate.getMinutes().toString().padStart(2, '0');
	let dateTime = 0;
	dateTime = year + month + day + hours + minutes;

	let url = `https://apihub.kma.go.kr/api/typ03/cgi/wrn/nph-wrn7?out=0&tmef=1&city=1&name=0&tm=${dateTime}&on=127.7&lat=36.1&range=300&size=685&wrn=W,R,C,D,O,V,T,S,Y,H,&authKey=tjD4kNMOSCqw-JDTDugq4g`;

	let weatherView = document.querySelector(".weatherView");
	weatherView.innerHTML = `<img src="${url}" alt="날씨 이미지"  width="410"/>`;
}

function updateRainFallImage() {
	console.log("updateRainFallImage() excuted")
	let currentDate = new Date();
	let year = currentDate.getFullYear();
	let month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
	let day = currentDate.getDate().toString().padStart(2, '0');
	let hours = currentDate.getHours().toString().padStart(2, '0');
	let minutes = currentDate.getMinutes().toString().padStart(2, '0');
	let dateTime = year + month + day + hours + minutes;

	let url = `https://www.hrfco.go.kr/servlet/sumun/rainfallSituationImg.do?Sdt=202311021510&Edt=${dateTime}`;

	let rainFallView = document.querySelector(".rainFallView");
	rainFallView.innerHTML = `<img src="${url}" alt="날씨 이미지" />`;
}

vmap.on("click", function(evt) {
	var coordinate = evt.coordinate;
	console.log(coordinate)
	var viewResolution = vmap.getView().getResolution();
	var wmsSource = GSLine.getSource();
	var url = wmsSource.getGetFeatureInfoUrl(
		coordinate, viewResolution, 'EPSG:3857',
		{ 'INFO_FORMAT': 'application/json', 'FEATURE_COUNT': 1, 'QUERY_LAYERS': 'sundo:line33' }
	);

	if (url) {
		$.getJSON(url, function(response) {
			if (response.features.length > 0) {
				var feature = response.features[0];
				console.log('Feature 정보:', feature.properties.bjcd);

				var GSLineLayer = new ol.layer.Image({
					source: new ol.source.ImageWMS({
						url: 'http://localhost:8080/geoserver/sundo/wms',
						params: {
							'LAYERS': 'sundo:line33',
							'FORMAT': 'image/png',
							'cql_filter': 'bjcd=' + feature.properties.bjcd
						},
					}),
				});

				if (removeNonBaseLayers.length !== 0) {
					removeNonBaseLayers.forEach(function(layer) {
						vmap.removeLayer(layer);
					});
				}
				removeNonBaseLayers.push(GSLineLayer);
				vmap.addLayer(GSLineLayer);

				let view = vmap.getView();
			if (feature.properties.bjcd === "4100000000") {
					// view.setCenter([14134718.970064852, 4512139.392021515]);
					view.setCenter([14137593.718925513, 4517460.1257873]);
					view.setZoom(8.7);
				} else {
					// view.setCenter([14134718.970064852, 4512139.392021515]);
					view.setCenter([14137593.718925513, 4517460.1257873]);
					view.setZoom(11)
				}

			} else {
				console.log('Feature를 찾을 수 없음');
			}
		}).fail(function() {
			console.error("GetFeatureInfo 요청 실패");
		});
	}
});

