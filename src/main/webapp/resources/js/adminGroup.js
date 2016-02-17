//Activate tooltips animation
$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})

/* 
 * Toggles to show/hide edit form
 * for the group name.
 * Hides all other forms.
 */
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

/* 
 * Toggles to show/hide edit form
 * for the group export.
 * Hides all other forms.
 */
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