$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})

function showEditName(show){	
	var span = $('#span-name');
	var div = $('#div-name');
	
	if(show){		
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}
	
}

function showExport(show){	
	var span = $('#span-export');
	var div = $('#div-export');
	
	if(show){		
		span.addClass("hidden");
		div.removeClass("hidden");
	}
	else{
		span.removeClass("hidden");
		div.addClass("hidden");
	}
	
}