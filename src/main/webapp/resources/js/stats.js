$(function () {	
	//Get context with jQuery - using jQuery's .get() method.
	var ctx = $("#myLineChart").get(0).getContext("2d");
	//Initialize chart.js options
	var options = {responsive: true};
	//Draw line chart
	var myLineChart = new Chart(ctx).Line(data, options);
	
	//Only for admins:
	if( typeof dataBar != "undefined"){		
		// Get context with jQuery - using jQuery's .get() method.
		var ctxBar = $("#myBarChart").get(0).getContext("2d");
		//Draw bar chart
		var myBarChart = new Chart(ctxBar).HorizontalBar(dataBar, options);

		//Override click function on chart to detect which bar is selected and redirect
		//to the stats of that lesson.
		$("#myBarChart").on('click',function(evt){
		    var activeBars = myBarChart.getBarsAtEvent(evt);
		    if( statsAdmin == "admin"){
		    	window.location= appHost + '/stats/admin/author/lesson/'+statsType+'/'+barIDs[activeBars[0]['label']];
		    }
		    else{
		    	window.location= appHost + '/stats/author/lesson/'+statsType+'/'+barIDs[activeBars[0]['label']];
		    }
		});

		//Draw pie chart
		var ctxPie = $("#myPieChart").get(0).getContext("2d");				
		var myPieChart = new Chart(ctxPie).Pie(dataPie,options);
	}
})

