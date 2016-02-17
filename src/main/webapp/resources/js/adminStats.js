$(function () {
	//Get context with jQuery - using jQuery's .get() method.
	var ctx = $("#myLineChart").get(0).getContext("2d");
	//Initialize chart.js options
	var options = {responsive: true};
	//Draw line chart
	var myLineChart = new Chart(ctx).Line(data, options);
	
	//Draw bar chart
	var ctxBar = $("#myBarChart").get(0).getContext("2d");
	var myBarChart = new Chart(ctxBar).HorizontalBar(dataBar, options);

	//Override click function on chart to detect which bar is selected and redirect
	//to the stats of that lesson.
	$("#myBarChart").on('click',function(evt){
	    var activeBars = myBarChart.getBarsAtEvent(evt);
	    window.location= appHost + '/stats/admin/lesson/'+statsType+'/'+barIDs[activeBars[0]['label']];		    
	});

	//Draw an horizontal bar chart
	var ctxBar2 = $("#myBarChart2").get(0).getContext("2d");
	var myBarChart2 = new Chart(ctxBar2).HorizontalBar(dataBar2, options);

	//Override click function on chart to detect which bar is selected and redirect
	//to the stats of that author.
	$("#myBarChart2").on('click',function(evt){
	    var activeBars = myBarChart2.getBarsAtEvent(evt);
	    window.location= appHost + '/stats/admin/author/'+statsType+'/'+bar2IDs[activeBars[0]['label']];		    
	});


})

