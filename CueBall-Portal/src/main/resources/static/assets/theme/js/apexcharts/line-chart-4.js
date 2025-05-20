(function ($) {
  
    var tfLineChartOne = (function () {
  
      var chartBar = function () {
      
        var optionsOne = {
            series: [
              {
                data: [20, 50, 7, 100, 30, 80, 100],
              },
            ],
            colors: ["#ffc107"],
            chart: {
              type: "area",
              maxWidth: 96,
              height: 28,
              sparkline: { enabled: !0 },
            },
            plotOptions: { bar: { columnWidth: "50%" } },
            xaxis: { crosshairs: { width: 1 } },
  
            stroke: {
              show: true,
              curve: "smooth",
              lineCap: "butt",
              colors: undefined,
              width: 3,
              dashArray: 0,
            },
            tooltip: {
              fixed: { enabled: !1 },
              x: { show: !1 },
              y: {
                title: {
                  formatter: function (e) {
                    return "";
                  },
                },
              },
              marker: { show: !1 },
            },
            states: {
              hover: {
                filter: {
                  type: "none",
                  value: 0,
                },
              },
            },
        },

        chart = new ApexCharts(
          document.querySelector("#line-chart-4"),
            optionsOne
        );
        if ($("#line-chart-4").length > 0) {
          chart.render();
        }
      };
  
      /* Function ============ */
      return {
        init: function () {},
  
        load: function () {
          chartBar();
        },
        resize: function () {},
      };
    })();
    var tfLineChartTwo = (function () {

        var chartBar = function () {

            var optionsTwo = {
                    series: [
                        {
                            data: [20, 50, 7, 100, 30, 80, 100],
                        },
                    ],
                    colors: ["#2275fc"],
                    chart: {
                        type: "area",
                        maxWidth: 96,
                        height: 28,
                        sparkline: { enabled: !0 },
                    },
                    plotOptions: { bar: { columnWidth: "50%" } },
                    xaxis: { crosshairs: { width: 1 } },

                    stroke: {
                        show: true,
                        curve: "smooth",
                        lineCap: "butt",
                        colors: undefined,
                        width: 3,
                        dashArray: 0,
                    },
                    tooltip: {
                        fixed: { enabled: !1 },
                        x: { show: !1 },
                        y: {
                            title: {
                                formatter: function (e) {
                                    return "";
                                },
                            },
                        },
                        marker: { show: !1 },
                    },
                    states: {
                        hover: {
                            filter: {
                                type: "none",
                                value: 0,
                            },
                        },
                    },
                },

                chart = new ApexCharts(
                    document.querySelector("#line-chart-23"),
                    optionsTwo
                );
            if ($("#line-chart-23").length > 0) {
                chart.render();
            }
        };

        /* Function ============ */
        return {
            init: function () {},

            load: function () {
                chartBar();
            },
            resize: function () {},
        };
    })();
    var tfLineChartThree = (function () {

        var chartBar = function () {

            var optionsThree = {
                    series: [
                        {
                            data: [20, 50, 7, 100, 30, 80, 100],
                        },
                    ],
                    colors: ["#22c55e"],
                    chart: {
                        type: "area",
                        maxWidth: 96,
                        height: 28,
                        sparkline: { enabled: !0 },
                    },
                    plotOptions: { bar: { columnWidth: "50%" } },
                    xaxis: { crosshairs: { width: 1 } },

                    stroke: {
                        show: true,
                        curve: "smooth",
                        lineCap: "butt",
                        colors: undefined,
                        width: 3,
                        dashArray: 0,
                    },
                    tooltip: {
                        fixed: { enabled: !1 },
                        x: { show: !1 },
                        y: {
                            title: {
                                formatter: function (e) {
                                    return "";
                                },
                            },
                        },
                        marker: { show: !1 },
                    },
                    states: {
                        hover: {
                            filter: {
                                type: "none",
                                value: 0,
                            },
                        },
                    },
                },

                chart = new ApexCharts(
                    document.querySelector("#line-chart-24"),
                    optionsThree
                );
            if ($("#line-chart-24").length > 0) {
                chart.render();
            }
        };

        /* Function ============ */
        return {
            init: function () {},

            load: function () {
                chartBar();
            },
            resize: function () {},
        };
    })();

    jQuery(document).ready(function () {});
  
    jQuery(window).on("load", function () {
        tfLineChartOne.load();
        tfLineChartTwo.load();
        tfLineChartThree.load();
    });
  
    jQuery(window).on("resize", function () {});
})(jQuery);