'use strict';$(document).ready(function(){function generateData(baseval,count,yrange){var i=0;var series=[];while(i<count){var x=Math.floor(Math.random()*(750-1+1))+1;;var y=Math.floor(Math.random()*(yrange.max-yrange.min+1))+yrange.min;var z=Math.floor(Math.random()*(75-15+1))+15;series.push([x,y,z]);baseval+=86400000;i++;}
return series;}
if($('#sales_chart').length>0){var columnCtx=document.getElementById("sales_chart"),columnConfig={colors:['#7638ff','#fda600'],series:[{name:"Received",type:"column",data:[70,150,80,180,150,175,201,60,200,120,190,160,50]},{name:"Pending",type:"column",data:[23,42,35,27,43,22,17,31,22,22,12,16,80]}],chart:{type:'bar',fontFamily:'Poppins, sans-serif',height:350,toolbar:{show:false}},plotOptions:{bar:{horizontal:false,columnWidth:'60%',endingShape:'rounded'},},dataLabels:{enabled:false},stroke:{show:true,width:2,colors:['transparent']},xaxis:{categories:['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct'],},yaxis:{title:{text:'$ (thousands)'}},fill:{opacity:1},tooltip:{y:{formatter:function(val){return "$ "+val+" thousands"}}}};var columnChart=new ApexCharts(columnCtx,columnConfig);columnChart.render();}
if($('#invoice_chart').length>0){var pieCtx=document.getElementById("invoice_chart"),pieConfig={colors:['#7638ff','#ff737b','#fda600','#1ec1b0'],series:[55,40,20,10],chart:{fontFamily:'Poppins, sans-serif',height:350,type:'donut',},labels:['Paid','Unpaid','Overdue','Draft'],legend:{show:false},responsive:[{breakpoint:480,options:{chart:{width:200},legend:{position:'bottom'}}}]};var pieChart=new ApexCharts(pieCtx,pieConfig);pieChart.render();}
if($('#s-line').length>0){var sline={chart:{height:350,type:'line',zoom:{enabled:false},toolbar:{show:false,}},dataLabels:{enabled:false},stroke:{curve:'straight'},series:[{name:"Desktops",data:[10,41,35,51,49,62,69,91,148]}],title:{text:'Product Trends by Month',align:'left'},grid:{row:{colors:['#f1f2f3','transparent'],opacity:0.5},},xaxis:{categories:['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep'],}}
var chart=new ApexCharts(document.querySelector("#s-line"),sline);chart.render();}
if($('#s-line-area').length>0){var sLineArea={chart:{height:350,type:'area',toolbar:{show:false,}},dataLabels:{enabled:false},stroke:{curve:'smooth'},series:[{name:'series1',data:[31,40,28,51,42,109,100]},{name:'series2',data:[11,32,45,32,34,52,41]}],xaxis:{type:'datetime',categories:["2018-09-19T00:00:00","2018-09-19T01:30:00","2018-09-19T02:30:00","2018-09-19T03:30:00","2018-09-19T04:30:00","2018-09-19T05:30:00","2018-09-19T06:30:00"],},tooltip:{x:{format:'dd/MM/yy HH:mm'},}}
var chart=new ApexCharts(document.querySelector("#s-line-area"),sLineArea);chart.render();}
if($('#s-col').length>0){var sCol={chart:{height:350,type:'bar',toolbar:{show:false,}},plotOptions:{bar:{horizontal:false,columnWidth:'55%',endingShape:'rounded'},},dataLabels:{enabled:false},stroke:{show:true,width:2,colors:['transparent']},series:[{name:'Net Profit',data:[44,55,57,56,61,58,63,60,66]},{name:'Revenue',data:[76,85,101,98,87,105,91,114,94]}],xaxis:{categories:['Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct'],},yaxis:{title:{text:'$ (thousands)'}},fill:{opacity:1},tooltip:{y:{formatter:function(val){return "$ "+val+" thousands"}}}}
var chart=new ApexCharts(document.querySelector("#s-col"),sCol);chart.render();}
if($('#s-col-stacked').length>0){var sColStacked={chart:{height:350,type:'bar',stacked:true,toolbar:{show:false,}},responsive:[{breakpoint:480,options:{legend:{position:'bottom',offsetX:-10,offsetY:0}}}],plotOptions:{bar:{horizontal:false,},},series:[{name:'PRODUCT A',data:[44,55,41,67,22,43]},{name:'PRODUCT B',data:[13,23,20,8,13,27]},{name:'PRODUCT C',data:[11,17,15,15,21,14]},{name:'PRODUCT D',data:[21,7,25,13,22,8]}],xaxis:{type:'datetime',categories:['01/01/2011 GMT','01/02/2011 GMT','01/03/2011 GMT','01/04/2011 GMT','01/05/2011 GMT','01/06/2011 GMT'],},legend:{position:'right',offsetY:40},fill:{opacity:1},}
var chart=new ApexCharts(document.querySelector("#s-col-stacked"),sColStacked);chart.render();}
    if ($('#s-bar').length > 0) {
        // Lấy dữ liệu ban đầu từ HTML
        const dropdownButton = document.getElementById('dropdownMenuButton');
        const barchartElement = document.getElementById('s-bar');

        // Lấy dữ liệu từ thuộc tính HTML
        const initialSeries = JSON.parse(dropdownButton.getAttribute('data-series'));
        const initialCategories = JSON.parse(dropdownButton.getAttribute('data-categories'));


        // Cấu hình biểu đồ
        const sBarOptions = {
            chart: {
                height: 350,
                type: 'bar',
                toolbar: { show: false }
            },
            plotOptions: {
                bar: {
                    horizontal: true
                }
            },
            dataLabels: {
                enabled: false
            },
            series: [{ data: initialSeries }],
            xaxis: {
                categories: initialCategories
            },
            colors: ['#008FFB']
        };

        // Khởi tạo biểu đồ
        const sBarChart = new ApexCharts(barchartElement, sBarOptions);

        sBarChart.render();

        // Xử lý sự kiện khi chọn một năm từ dropdown
        const dropdownItems = document.querySelectorAll('.dropdown-item');
        dropdownItems.forEach(item => {
            item.addEventListener('click', function () {
                // Lấy năm được chọn và dữ liệu tương ứng
                const selectedYear = this.getAttribute('data-year');
                const updatedSeries = JSON.parse(this.getAttribute('data-series'));
                const updatedCategories = JSON.parse(this.getAttribute('data-categories'));

                // Cập nhật nội dung hiển thị của nút dropdown
                document.getElementById('selectedYear').textContent = selectedYear === 'all' ? 'Tất Cả' : selectedYear;

                // Cập nhật dữ liệu biểu đồ
                sBarChart.updateOptions({
                    series: [{ data: updatedSeries }],
                    xaxis: {
                        categories: updatedCategories
                    }
                });

                // Cập nhật dữ liệu trên nút dropdown
                dropdownButton.setAttribute('data-series', JSON.stringify(updatedSeries));
                dropdownButton.setAttribute('data-categories', JSON.stringify(updatedCategories));
            });
        });
    }
    if ($('#mixed-chart').length > 0) {
        // Lấy dữ liệu ban đầu từ nút dropdown
        const dropdownButton = document.getElementById('dropdownMenuButton');
        const initialRevenueData = JSON.parse(dropdownButton.getAttribute('data-series-revenue'));
        const initialSalesData = JSON.parse(dropdownButton.getAttribute('data-series-sales'));
        const initialLabels = JSON.parse(dropdownButton.getAttribute('data-series-label'));
        // Cấu hình biểu đồ
        var options = {
            chart: { height: 350, type: 'line', toolbar: { show: false } },
            series: [
                { name: 'Doanh Thu', type: 'column', data: initialRevenueData },
                { name: 'Đơn Bán Ra', type: 'line', data: initialSalesData }
            ],
            stroke: { width: [0, 4] },
            title: { text: 'Doanh thu và đơn bán', style: { fontWeight: 'bold', fontFamily: 'Arial, sans-serif' } },
            labels: initialLabels, // Dùng các nhãn (labels) ban đầu
            xaxis: { type: 'text' },
            yaxis: [
                { title: { text: 'Doanh Thu', style: { fontSize: '15px', fontWeight: 'bold', fontFamily: 'Arial, serif' } } },
                { opposite: true, title: { text: 'Đơn Bán', style: { fontSize: '15px', fontWeight: 'bold', fontFamily: 'Arial, serif' } } }
            ]
        };

        // Khởi tạo biểu đồ
        var chart = new ApexCharts(document.querySelector("#mixed-chart"), options);
        chart.render();

        // Xử lý sự kiện click vào mục dropdown
        const dropdownItems = document.querySelectorAll('.dropdown-item');
        dropdownItems.forEach(item => {
            item.addEventListener('click', function () {
                const selectedYear = this.textContent; // Lấy năm từ nội dung của mục
                let revenueData = JSON.parse(this.getAttribute('data-series-revenue')); // Lấy dữ liệu revenue
                let salesData = JSON.parse(this.getAttribute('data-series-sales')); // Lấy dữ liệu sales

                // Nếu chọn "Tất Cả", cập nhật trục x là các năm thay vì các tháng
                if (selectedYear.includes("Tất Cả")) {
                    const revenueAllData = [20,30,40,50]
                    const salesAllData = [20,30,40,50]
                    chart.updateOptions({
                        labels: years,  // Nhãn trục x là các năm
                        series: [
                            { name: 'Doanh Thu', type: 'column', data: revenueAllData },
                            { name: 'Đơn Bán Ra', type: 'line', data: salesAllData }
                        ]
                    });
                } else {
                    // Cập nhật biểu đồ cho dữ liệu của năm cụ thể
                    chart.updateOptions({

                        series: [
                            { name: 'Doanh Thu', type: 'column', data: revenueData },
                            { name: 'Đơn Bán Ra', type: 'line', data: salesData }
                        ]
                    });
                }

                // Cập nhật năm đã chọn
                document.getElementById('selectedYear').textContent = selectedYear;

                // Cập nhật dữ liệu ban đầu trên nút dropdown
                dropdownButton.setAttribute('data-series-revenue', JSON.stringify(revenueData));
                dropdownButton.setAttribute('data-series-sales', JSON.stringify(salesData));
            });
        });
    }
if($('#donut-chart').length>0){var donutChart={chart:{height:350,type:'donut',toolbar:{show:false,}},series:[44,55,41,17],responsive:[{breakpoint:480,options:{chart:{width:200},legend:{position:'bottom'}}}]}
var donut=new ApexCharts(document.querySelector("#donut-chart"),donutChart);donut.render();}
if($('#radial-chart').length>0){var radialChart={chart:{height:350,type:'radialBar',toolbar:{show:false,}},plotOptions:{radialBar:{dataLabels:{name:{fontSize:'22px',},value:{fontSize:'16px',},total:{show:true,label:'Total',formatter:function(w){return 249}}}}},series:[44,55,67,83],labels:['Apples','Oranges','Bananas','Berries'],}
var chart=new ApexCharts(document.querySelector("#radial-chart"),radialChart);chart.render();}
    if ($('#sales_charts_status').length > 0) {
        // Lấy dữ liệu ban đầu từ HTML

        const chartElement = document.getElementById('sales_charts_status');
        const dropdownButton = document.getElementById('dropdownMenuButton');
        const initialCompletedData = JSON.parse(dropdownButton.getAttribute('data-completed'));
        const initialCanceledData = JSON.parse(dropdownButton.getAttribute('data-canceled'));
        const initialXAxis = JSON.parse(dropdownButton.getAttribute('data-xaxis'));
        // Cấu hình biểu đồ
        const options = {
            series: [
                { name: 'Hoàn Thành', data: initialCompletedData },
                { name: 'Đã Hủy', data: initialCanceledData }
            ],
            colors: ['#28C76F', '#EA5455'],
            chart: {
                type: 'bar',
                height: 300,
                stacked: true,
                zoom: { enabled: true }
            },
            plotOptions: {
                bar: { horizontal: false, columnWidth: '20%', endingShape: 'rounded' }
            },
            xaxis: {
                categories: initialXAxis // Sử dụng giá trị cột X từ HTML
            },
            legend: {
                position: 'right',
                offsetY: 40
            },
            fill: { opacity: 1 }
        };

        // Khởi tạo biểu đồ
        const chart = new ApexCharts(chartElement, options);
        chart.render();

        // Xử lý sự kiện khi chọn một năm từ dropdown
        const dropdownItems = document.querySelectorAll('.dropdown-item');
        dropdownItems.forEach(item => {
            item.addEventListener('click', function () {
                const selectedYear = this.getAttribute('data-year');
                const completedData = JSON.parse(this.getAttribute('data-completed'));
                const canceledData = JSON.parse(this.getAttribute('data-canceled'));
                // Cập nhật nút dropdown và biểu đồ
                document.getElementById('selectedYear').textContent = selectedYear === 'all' ? 'Tất Cả' : selectedYear;
                chart.updateSeries([
                    { name: 'Hoàn Thành', data: completedData },
                    { name: 'Đã Hủy', data: canceledData }
                ]);

                // Cập nhật dữ liệu trên nút dropdown
                dropdownButton.setAttribute('data-completed', JSON.stringify(completedData));
                dropdownButton.setAttribute('data-canceled', JSON.stringify(canceledData));
            });
        });
    }
    if ($('#sales_charts').length > 0) {
        // Lấy dữ liệu ban đầu từ HTML

        const chartElement = document.getElementById('sales_charts');
        const dropdownButton = document.getElementById('dropdownMenuButton');
        const initialCompletedData = JSON.parse(dropdownButton.getAttribute('data-completed'));
        const initialCanceledData = JSON.parse(dropdownButton.getAttribute('data-canceled'));
        const initialXAxis = JSON.parse(dropdownButton.getAttribute('data-xaxis'));
        // Cấu hình biểu đồ
        const options = {
            series: [
                { name: 'Hoàn Thành', data: initialCompletedData },
                { name: 'Đã Hủy', data: initialCanceledData }
            ],
            colors: ['#28C76F', '#EA5455'],
            chart: {
                type: 'bar',
                height: 300,
                stacked: true,
                zoom: { enabled: true }
            },
            plotOptions: {
                bar: { horizontal: false, columnWidth: '20%', endingShape: 'rounded' }
            },
            xaxis: {
                categories: initialXAxis // Sử dụng giá trị cột X từ HTML
            },
            legend: {
                position: 'right',
                offsetY: 40
            },
            fill: { opacity: 1 }
        };

        // Khởi tạo biểu đồ
        const chart = new ApexCharts(chartElement, options);
        chart.render();

        // Xử lý sự kiện khi chọn một năm từ dropdown
        const dropdownItems = document.querySelectorAll('.dropdown-item');
        dropdownItems.forEach(item => {
            item.addEventListener('click', function () {
                const selectedYear = this.getAttribute('data-year');
                const completedData = JSON.parse(this.getAttribute('data-completed'));
                const canceledData = JSON.parse(this.getAttribute('data-canceled'));
                // Cập nhật nút dropdown và biểu đồ
                document.getElementById('selectedYear').textContent = selectedYear === 'all' ? 'Tất Cả' : selectedYear;
                chart.updateSeries([
                    { name: 'Hoàn Thành', data: completedData },
                    { name: 'Đã Hủy', data: canceledData }
                ]);

                // Cập nhật dữ liệu trên nút dropdown
                dropdownButton.setAttribute('data-completed', JSON.stringify(completedData));
                dropdownButton.setAttribute('data-canceled', JSON.stringify(canceledData));
            });
        });
    }});
