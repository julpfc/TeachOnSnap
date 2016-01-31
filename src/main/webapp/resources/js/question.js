$(document).ready(function() {
	$('#questionForm').on('submit', function(event) {
		$('#json').prop('name','json');
		$('#json').val(JSON.stringify(form2js('questionForm', '.', true)));	
    });

	$('input[type=radio]').change(function() {
		var notThisInput = $('input[type=radio]:checked').not(this);
		notThisInput.prop('checked', false);
		$(this).closest('tr').addClass('list-group-item-success');		
		notThisInput.closest('tr').removeClass('list-group-item-success');
	});
	
	$('input[type=checkbox]').change(function() {
		if($(this).prop('checked')){
			$(this).closest('tr').addClass('list-group-item-success');	
		}
		else{
			$(this).closest('tr').removeClass('list-group-item-success');
		}				
	});
	
	
});

function importJSON() {	
	var json = $('#JSONarea').val();
	$('#jsonAlert').alert('close');
	
	if(json){
		try {
			var question = jQuery.parseJSON(json);		
			if(question){
				$('input[name="text"]').val(question.text);
				$.each(question.answers, function(i) {
					$('input[name="answers['+i+'].text"]').val(question.answers[i].text);
					$('input[name="answers['+i+'].reason"]').val(question.answers[i].reason);
					if(question.answers[i].correct){
						$('input[name="answers['+i+'].correct"]').trigger('click');	
					}
				});
			}
		}
		catch(err){			
			$('#collapseImportJSON').prepend('<div id="jsonAlert" class="alert alert-danger alert-dismissible fade in" role="alert">'
						+'<button type="button" class="close" data-dismiss="alert" aria-label="Close">'
						+'<span aria-hidden="true">×</span></button><strong>Ups! </strong>'
						+'<span id="jsonError"></p></span>');
			$('#jsonError').text(err);			
		}
	}
	
}

