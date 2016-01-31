$(function () {
	
	// Get context with jQuery - using jQuery's .get() method.
	var ctx = $("#myLineChart").get(0).getContext("2d");

	var options = {responsive: true};
	
	var myLineChart = new Chart(ctx).Line(data, options);
	
	var ctxBar = $("#myBarChart").get(0).getContext("2d");
	var myBarChart = new Chart(ctxBar).HorizontalBar(dataBar, options);

	$("#myBarChart").on('click',function(evt){
	    var activeBars = myBarChart.getBarsAtEvent(evt);
	    window.location= appHost + '/stats/admin/lesson/'+statsType+'/'+barIDs[activeBars[0]['label']];		    
	});

	var ctxBar2 = $("#myBarChart2").get(0).getContext("2d");
	var myBarChart2 = new Chart(ctxBar2).HorizontalBar(dataBar2, options);

	$("#myBarChart2").on('click',function(evt){
	    var activeBars = myBarChart2.getBarsAtEvent(evt);
	    window.location= appHost + '/stats/admin/author/'+statsType+'/'+bar2IDs[activeBars[0]['label']];		    
	});


})

