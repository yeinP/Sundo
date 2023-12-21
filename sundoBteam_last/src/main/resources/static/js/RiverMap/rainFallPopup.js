var rfobscd;
var searchType = "10M";

var startToday = new Date();
startToday.setDate(startToday.getDate()-1);
var syear = startToday.getFullYear();
var smonth = (startToday.getMonth() + 1).toString().padStart(2, '0');
var sday = startToday.getDate().toString().padStart(2, '0');
var startD = syear + smonth + sday;

var endToday = new Date();
var eyear = endToday.getFullYear();
var emonth = (endToday.getMonth()+1).toString().padStart(2, '0');
var eday = endToday.getDate().toString().padStart(2, '0');
var eminutes = endToday.getMinutes();
var ehour = endToday.getHours();
var ehourString = ehour.toString().padStart(2, '0');
var adjustMString = eminutes.toString().padStart(2, '0');
var endD = eyear + emonth + eday + ehourString + adjustMString;

$(document).ready(function() {
    rfobscd = $("#rfobscd").text();
    console.log(rfobscd);
    setDefaultDates();
    fetchAndDisplayData(searchType, rfobscd, startD, endD);

});

$(".dampop_il").click(function() {
    searchType = $(this).attr("value");
    console.log(searchType);
    fetchAndDisplayData(searchType, rfobscd, startD, endD);
});

function setDefaultDates() {
    var currentDate = new Date();
    var yesterday = new Date(currentDate);
    yesterday.setDate(currentDate.getDate() - 1);
    var startDate = formatDate(yesterday);
    var endDate = formatDate(currentDate);
    $("#startDate").val(startDate);
    $("#endDate").val(endDate);
}
function formatDate(date) {
    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0');
    var day = date.getDate().toString().padStart(2, '0');
    return year + "-" + month + "-" + day;
}

//마지막날짜 오늘로 (input date 할 땐 필요할 듯)
document.addEventListener("DOMContentLoaded", function() {
    var endDateInput = document.getElementById("endDate");
    var today = new Date();
    var year = today.getFullYear();
    var month = (today.getMonth() + 1).toString().padStart(2, '0');
    var day = today.getDate().toString().padStart(2, '0');
    var maxDate = year + "-" + month + "-" + day;
    endDateInput.setAttribute("max", maxDate);
});

function formatYMDHM(ymdhm) {
    if (ymdhm.length === 12) {
        var year = ymdhm.slice(0, 4);
        var month = ymdhm.slice(4, 6);
        var day = ymdhm.slice(6, 8);
        var hour = ymdhm.slice(8, 10);
        var minute = ymdhm.slice(10, 12);
        return year + '.' + month + '.' + day + ' ' + hour + ':' + minute;
    } if(ymdhm.length === 10){
        var year = ymdhm.slice(0, 4);
        var month = ymdhm.slice(4, 6);
        var day = ymdhm.slice(6, 8);
        var hour = ymdhm.slice(8, 10);
        return year + '.' + month + '.' + day + ' ' + hour + '시' ;
    } if(ymdhm.length === 8) {
        var year = ymdhm.slice(0, 4);
        var month = ymdhm.slice(4, 6);
        var day = ymdhm.slice(6, 8);
        return year + '.' + month + '.' + day;
    } else {
        return ymdhm;
    }
}

function fetchAndDisplayData(searchType, rfobscd, startD, endD) {
    var apiUrl = "http://api.hrfco.go.kr/8794F4D7-ED45-43EB-8E2F-1B721623F802/rainfall/list/" +  searchType + "/" + rfobscd + "/" + startD + "0000" + "/" + endD + ".json";

    console.log(apiUrl);
    var table = document.getElementById("tableTitle");
    var headerRow = table.rows[0];
    var headers = [];
    for (var i = 0; i < headerRow.cells.length; i++) {
        headers.push(headerRow.cells[i].textContent);
    }
    console.log("1--------------------------------")
    console.log(headers)
    const index1 = findRowIndexByValue(startD, headers)


    $.get(apiUrl, function(result) {
        $("table tbody").empty();
        var table = "";
        var rainFall = [];


        // 데이터를 테이블에 추가
        $.each(result.content, function(index, value) {
            table += "<tr>";
            table += "<th class='f'>" + formatYMDHM(value.ymdhm) + "</th>";
            table += "<td>" + (value.rf === " " ? '-' : parseFloat(value.rf).toFixed(1)) + "</td>";
            rainFall.unshift(parseFloat(value.rf));
            table += "</tr>";

        });

        $("table tbody").append(table);

        const date1 = document.getElementById("startDate").value;
        const newDate1 = new Date(date1);
        const date1Year = newDate1.getFullYear();
        const date1Month = String(newDate1.getMonth());
        const date1Day = String(newDate1.getDate());


        const date2 = document.getElementById("endDate").value;
        const newDate2 = new Date(date2);
        const date2Year = newDate2.getFullYear();
        const date2Month = String(newDate2.getMonth());
        const date2Day = String(newDate2.getDate());
        var date2Hour = endToday.getHours();
        var date2Min = endToday.getMinutes();

        const minYValue = Math.min(...rainFall)-1;
        const maxYValue = Math.max(...rainFall)+1;


        Highcharts.chart(
            'graph',
            {
                chart : {
                    type : 'spline',
                    height: 300,
                },
                title : {
                    text: "강수량"
                },
                xAxis : {
                    type : 'datetime',
                    labels : {
                        overflow : 'justify',
                    },
                    max: Date.UTC(date2Year, date2Month, date2Day, date2Hour, date2Min, 0),
                    tickInterval: getTickInterval(searchType),
                },
                yAxis : {
                    min : minYValue,
                    max: maxYValue,
                    tickInterval: 1,
                    minorGridLineWidth : 0,
                    gridLineWidth : 0,
                    alternateGridColor : null,
                    plotBands : [
                        {
                            from : 0.3,
                            to : 1.5,
                            color : 'rgba(68, 170, 213, 0.1)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 1.5,
                            to : 3.3,
                            color : 'rgba(0, 0, 0, 0)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 3.3,
                            to : 5.5,
                            color : 'rgba(68, 170, 213, 0.1)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 5.5,
                            to : 8,
                            color : 'rgba(0, 0, 0, 0)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 8,
                            to : 11,
                            color : 'rgba(68, 170, 213, 0.1)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 11,
                            to : 14,
                            color : 'rgba(0, 0, 0, 0)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 14,
                            to : 17,
                            color : 'rgba(68, 170, 213, 0.1)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 17,
                            to : 20.5,
                            color : 'rgba(0, 0, 0, 0)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        },
                        {
                            from : 20.5,
                            to : 24,
                            color : 'rgba(68, 170, 213, 0.1)',
                            label : {
                                style : {
                                    color : '#606060'
                                }
                            }
                        } ]
                },
                tooltip : {
                    headerFormat : '',
                    pointFormat : getPointFormat(searchType),
                },
                plotOptions : {
                    spline : {
                        lineWidth : 4,
                        states : {
                            hover : {
                                lineWidth : 5
                            }
                        },
                        marker : {
                            enabled : false
                        },
                        pointInterval : getPointInterval(searchType),
                        pointStart : Date
                            .UTC(date1Year, date1Month, date1Day, 0, 0, 0),

                    }
                },
                series : [
                    {
                        name : '강수량',
                        data : rainFall,
                        tooltip : {
                            valueSuffix : 'm',
                            pointFormat: getPointFormat(searchType)
                        }

                    },
                ],
                navigation : {
                    menuItemStyle : {
                        fontSize : '10px'
                    }
                }
            });

        function getPointFormat(searchType) {
            var commonFormat = '<br><span style="color:{point.color}">\u25CF</span> {series.name}: <b>{point.y:.1f}</b>';
            if (searchType === '10M') {
                return '{point.x:%Y-%m-%d %H:%M}' + commonFormat;
            } else if (searchType === '1H') {
                return '{point.x:%Y-%m-%d %H}' + commonFormat;
            } else if (searchType === '1D') {
                return '{point.x:%Y-%m-%d}' + commonFormat;
            }
        }
        function getTickInterval(searchType) {
            if (searchType === '10M') {
                return 600000; // 10분 간격
            } else if (searchType === '1H') {
                return 3600000; // 1시간 간격
            } else if (searchType === '1D') {
                return 24 * 3600000; // 1일 간격
            }
        }

        function getPointInterval(searchType){
            if (searchType === '10M') {
                return 600000; // 10분 간격
            } else if (searchType === '1H') {
                return 3600000; // 1시간 간격
            } else if (searchType === '1D') {
                return 24 * 3600000; // 1일 간격
            }
        }

        // var time = $(this).find('th').text();
        console.log ("차트")
        console.log(searchType);
    });

}

$(".searchBtn").click(function() {
    const date1 = document.getElementById("startDate").value;
    const newDate1 = new Date(date1);
    const date1Year = newDate1.getFullYear();
    const date1Month = String(newDate1.getMonth()+1).padStart(2,'0');
    const date1Day = String(newDate1.getDate()).padStart(2,'0');
    const searchDate1 = date1Year + date1Month + date1Day;
    startD = searchDate1;


    const date2 = document.getElementById("endDate").value;
    const newDate2 = new Date(date2);
    const currentDate = new Date();

    const date2Year = newDate2.getFullYear();
    const date2Month = String(newDate2.getMonth()+1).padStart(2,'0');
    const date2Day = String(newDate2.getDate()).padStart(2,'0');
    var date2Hour = endToday.getHours();
    var date2Min = endToday.getMinutes();
    if (newDate2.getFullYear() !== currentDate.getFullYear() ||
        newDate2.getMonth() !== currentDate.getMonth() ||
        newDate2.getDate() !== currentDate.getDate()) {
        date2Hour = '23';
        date2Min = '50';
    }

    var ehourString = date2Hour.toString().padStart(2, '0');
    var adjustMString = date2Min.toString().padStart(2, '0');
    const searchDate2 = date2Year + date2Month + date2Day + ehourString + adjustMString;
    endD = searchDate2

    const dayDifference = calculateDateDifference();
    if(dayDifference >= 30) {
        alert('전체 기간 조회는 30일 이내여야 합니다');
    } else {
        fetchAndDisplayData(searchType, rfobscd, startD, endD);
    }


});

const startDateInput = document.getElementById("startDate");
const endDateInput = document.getElementById("endDate");
function calculateDateDifference() {
    const startDateValue = new Date(startDateInput.value);
    const endDateValue = new Date(endDateInput.value);
    const timeDifference = endDateValue - startDateValue; // 밀리초 단위로 차이를 계산
    const dayDifference = timeDifference / (1000 * 60 * 60 * 24); // 일 단위로 변환

    return dayDifference;
}


function findRowIndexByValue(valueToFind, data) {
    for (let i = 0; i < data.length; i++) {
        if (data[i][0] === valueToFind) {
            return i;
        }
    }
    return -1;
}