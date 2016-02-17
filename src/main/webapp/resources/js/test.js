$(document).ready(function() {
	
	/* 
	 * Handles a hidden input choice type, to
	 * manage clicks on test's answers. 
	 */
    var choices = $('.answer');
    choices.on('click', function(event) {
        var choice = $(event.target);
        
        var input = choice.find("input");
        
        if(input.prop('type') == "radio"){
        	if (!input.prop('disabled')){
        		choice
	        		.find("input")
		            .prop('checked', true)	            
		            .trigger('changeRadio');
        	}
        }
        else{
        	if (!input.prop('disabled')){
	        	input
	            	.prop('checked', !input.is(':checked'))	            
	            	.trigger('changeCheckBox');
        	}
        }
    });
    
    /*
     * Toggle answer status
     */
    var inputs = $('.answer input');
    inputs.on('changeRadio', function(event) {
        var input = $(event.target);
        var choice = $(this).closest('.answer');
        var question = $(this).closest('.question');
                
        question
        	.find('.answer.active')
        	.removeClass('active');
        
        choice.addClass('active');
    });
    
    /*
     * Toggle hidden input choice status
     */
    inputs.on('changeCheckBox', function(event) {
        var input = $(event.target);
        var choice = $(this).closest('.answer');
        
        if (input.is(':checked')) {
            choice.addClass('active');
        }
        else {
            choice.removeClass('active');
        }
    });
});