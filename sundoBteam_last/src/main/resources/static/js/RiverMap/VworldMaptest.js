 var pop = null;
vw.ol3.CameraPosition.center = [14134718.970064852, 4512139.392021515];
vw.ol3.CameraPosition.zoom =10;
vw.ol3.MapOptions = {
    basemapType: vw.ol3.BasemapType.GRAPHIC,
    controlDensity: vw.ol3.DensityType.EMPTY,
    interactionDensity: vw.ol3.DensityType.FULL,
    controlsAutoArrange: true,
    homePosition: vw.ol3.CameraPosition,
    initPosition: vw.ol3.CameraPosition,
};

var vmap = new vw.ol3.Map("vmap", vw.ol3.MapOptions);
markerLayer = new vw.ol3.layer.Marker(vmap);
vmap.addLayer(markerLayer);
// addMarker();

var response;


function addMarker(code ,longitude, latitude, text) {
    var markerOption = {
        x: longitude,
        y: latitude,
        epsg: "EPSG:4326",
        title: text, // 마커 제목
        iconUrl: '//map.vworld.kr/images/ol3/marker_blue.png',
        contents : '수위 정보 ' +
            '       강수량 정보',
        text: {
            offsetX: 0.5,
            offsetY: 20,
            font: '12px Calibri,sans-serif',
            fill: { color: '#000' },
            stroke: { color: '#fff', width: 2 },
            text: text
        },
        attr: { "id": code, "name": text},

    };

    var marker =  markerLayer.addMarker(markerOption);

    for (var i = 0; i < response.length; i++) {
        var markerData = response[i];
        var longitude = markerData.r_longitude;
        var latitude = markerData.r_latitude;
        var text = markerData.r_name;
        var code = markerData.r_code;

        addMarker(code, longitude, latitude, text);

        // 클릭 이벤트 핸들러 등록
        marker.on('click', function (evt) {
            var markerData = evt.target.data;
            console.log("클릭" + markerData.code);

            // 클릭한 마커의 r_code를 가져와서 API URL을 생성
            var rCode = markerData.code;
            var apiUrl = "http://api.hrfco.go.kr/52832662-D130-4239-9C5F-730AD3BE6BC6/waterlevel/list/10M/" + rCode + "/202311031910/202311041950.json";

            // API를 호출하여 정보 가져오기
            $.get(apiUrl, function (data) {
                // API로부터 받은 JSON 데이터를 여기에서 처리
                console.log("API 응답 데이터:", data);

                // 원하는 정보를 화면에 표시하도록 처리
                var waterLevel = data.waterLevel; // 예시: JSON에서 실제 필드 이름으로 변경
                var rainfall = data.rainfall; // 예시: JSON에서 실제 필드 이름으로 변경

                // 정보를 화면에 표시
                var infoContent = "수위 정보: " + waterLevel + "<br>강수량 정보: " + rainfall;
                openWindowPop(infoContent, markerData.name, 800, 800);
            });
        });
    }

    marker.data = {
        code: code,
        name: text
    };
}


vmap.on('singleclick', function (evt){
    var feature = vmap.forEachFeatureAtPixel(evt.pixel, function (feature, layer) {
        return feature;
    });

    if (feature) {
        var markerData = feature.data;
        console.log("싱글클릭");
        openWindowPop("http://hrfco.go.kr/popup/cctvMainView.do?Obscd="+markerData.code, markerData.name, 700, 700);
    }
    // var clickPop = vmap.hasFeatureAtPixel(evt.pixel);
    // if (clickPop == true) {
    //     openWindowPop("/detail/river/{r_code}(r_code=", "강", 700, 700, );
    // }
});

 var popupWindow;

function openWindowPop(url, title, w, h) {
    if (popupWindow && !popupWindow.closed) {
        popupWindow.close();
    }
    const left = (window.screen.width - w) / 2;
    const top = (window.screen.height - h) / 2;
    const popupWindow = window.open(
        url,
        title,
        `width=${w}, height=${h}, top=${top}, left=${left}`);

    if (window.focus) {
        popupWindow.focus();
    }
}
function openInfoWindow(marker) {
    // 콘솔에 메시지 출력
    console.log("마커를 클릭했습니다. 마커 정보: " + marker.get("name"));
    // 실제로는 alert 대신 팝업을 열어 마커 정보를 표시해야 합니다.
}

// 마커 클릭 이벤트 핸들러 등록

$.getJSON("/HanRiverMap/marker",function (response){

    // 요청이 성공적으로 완료된 경우 이 함수가 호출됨
    // response는 서버에서 반환한 JSON 데이터를 나타냄
    console.log(response)
    // JSON 데이터를 처리하고 마커를 추가하는 로직을 여기에 작성
    for (var i = 0; i < response.length; i++) {
        var markerData = response[i];
        var longitude = markerData.r_longitude;
        var latitude = markerData.r_latitude;
        var text = markerData.r_name;
        var code = markerData.r_code;
        // 마커를 추가하고 위도/경도를 사용하여 지도에 표시
        addMarker(code ,longitude, latitude, text);
    }
})



// vmap.on("click", function(evt) {
//     var coordinate = evt.coordinate;
//     //var hdms = ol.coordinate.toStringHDMS(ol.proj.transform(coordinate,"EPSG:3857","EPSG:4326"));
//     var hdms = "123";
//     var content = hdms ;
//     var pixelTolerance = 10; // 클릭한 픽셀 주변의 여유 공간
//     var extent = vmap.getView().calculateExtent(vmap.getSize());
//     var viewResolution = vmap.getView().getResolution();
//     // var coordinate = evt.coordinate;
//
//     // 클릭한 위치의 좌표를 GeoServer에 전달하여 GetFeatureInfo 요청을 보냄
//     var wmsSource = cityLayer.getSource();
//     var url = wmsSource.getGetFeatureInfoUrl(
//         evt.coordinate, viewResolution, 'EPSG:3857',
//         { 'INFO_FORMAT': 'application/json', 'FEATURE_COUNT': 1, 'QUERY_LAYERS': 'gis:lsmd_cont_uj201_11_202310' }
//     );
//
//     if (url) {
//         // GetFeatureInfo 요청을 보내고 응답을 처리
//         $.getJSON(url, function(response) {
//             if (response.features.length > 0) {
//
//                 // vmap.removeLayer(sgglayer);
//                 // Feature 정보를 여기에서 처리
//                 var feature = response.features[0];
//                 console.log('Feature 정보:', feature.properties.sgg_oid);
//                 // 'cql_filter': 'sgg_oid='+feature.properties.sgg_oid;
//                 sgglayer = new ol.layer.Image({
//                     source: new ol.source.ImageWMS({
//                         url: 'http://localhost:9898/geoserver/gis/wms',
//                         params: {
//                             'LAYERS': 'gis:lsmd_cont_uj201_11_202310',
//                             'FORMAT': 'image/png',
//                             'cql_filter': 'sgg_oid='+feature.properties.sgg_oid
//
//                         },
//                     }),
//                 });
//                 vmap.removeLayer(cityLayer);
//                 vmap.addLayer(sgglayer);
//             } else {
//                 console.log('Feature를 찾을 수 없음');
//             }
//         });
//     }
//
//     if (pop == null) {
//         pop = new vw.ol3.popup.Popup();
//     }
//     pop.title = "sample";
//     pop.content = "sample";
//     vmap.addOverlay(pop);
//     pop.show(content, coordinate);
// });

var seoulRiver;
document.getElementById('seoul_river').addEventListener('click', function () {
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
document.getElementById('gyeonggi_river').addEventListener('click', function () {
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

var seouldrainage;
document.getElementById('seoul_drainage').addEventListener('click', function () {
    if (!seouldrainage) {
        seouldrainage = new ol.layer.Image({
            source: new ol.source.ImageWMS({
                url: 'http://localhost:8080/geoserver/sundo/wms',
                params: {
                    'LAYERS': 'sundo:lsmd_cont_um730_11_202310',
                    'FORMAT': 'image/png',

                },
            }),
        });

        vmap.addLayer(seouldrainage);
    } else {
        vmap.removeLayer(seouldrainage);
        seouldrainage = undefined;
    }
});

var gyeonggidrainage;
document.getElementById('gyeonggi_drainage').addEventListener('click', function () {
    if (!gyeonggidrainage) {
        gyeonggidrainage = new ol.layer.Image({
            source: new ol.source.ImageWMS({
                url: 'http://localhost:8080/geoserver/sundo/wms',
                params: {
                    'LAYERS': 'sundo:lsmd_cont_um730_41_202310',
                    'FORMAT': 'image/png',

                },
            }),
        });

        vmap.addLayer(gyeonggidrainage);
    } else {
        vmap.removeLayer(gyeonggidrainage);
        gyeonggidrainage = undefined;
    }
});

