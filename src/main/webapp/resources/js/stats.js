$(function () {
	
	// Get context with jQuery - using jQuery's .get() method.
	var ctx = $("#myLineChart").get(0).getContext("2d");

	var options = {responsive: true};
	
	var myLineChart = new Chart(ctx).Line(data, options);
	
	if( typeof dataBar != "undefined"){
		var ctxBar = $("#myBarChart").get(0).getContext("2d");
		var myBarChart = new Chart(ctxBar).HorizontalBar(dataBar, options);

		$("#myBarChart").on('click',function(evt){
		    var activeBars = myBarChart.getBarsAtEvent(evt);
		    if( statsAdmin == "admin"){
		    	window.location= appHost + '/stats/admin/author/lesson/'+statsType+'/'+barIDs[activeBars[0]['label']];
		    }
		    else{
		    	window.location= appHost + '/stats/author/lesson/'+statsType+'/'+barIDs[activeBars[0]['label']];
		    }
		});

		var ctxPie = $("#myPieChart").get(0).getContext("2d");				
		var myPieChart = new Chart(ctxPie).Pie(dataPie,options);
	}

})

