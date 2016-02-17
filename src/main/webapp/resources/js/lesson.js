
//Initialize DOM references
var commentNew = $('#commentNew');
var collapseComment = $('#collapseNewComment');
var form = collapseComment.find("form");
var textarea = collapseComment.find("textarea");
var commentHeading = commentNew.find("a");
var commentNewAnchor = $('#commentNewAnchor');
var button = collapseComment.find("button");

var originalHeading = undefined;
var originalURL = undefined;
var originalButton = undefined;
var banButton = '<span class="glyphicon glyphicon-ban-circle"></span> '

/*
 * Moves the comment form to the selected comment in order to edit it.
 */	
function moveCommentForm(commentId, heading, commentBody, buttonText){
	if(originalHeading){
		resetForm();
	}
	var comment = $('#comment-'+commentId);
		
	commentNew.insertAfter(comment);
	
	if(!originalURL){
		originalURL = form.prop('action');
	}
	
	if(!originalButton){
		originalButton = button.prop('innerHTML');
	}

	if(buttonText){
		button.prop('innerHTML',banButton+buttonText);
		form.prop('action',form.prop('action')+'?idComment='+commentId+'&banComment=true');
	}
	else if(commentBody){
		var commentContent = comment.find(".comment-content");
		textarea.prop('textContent',convertFromHTMLParagraph(commentContent.prop('innerHTML')));
		form.prop('action',form.prop('action')+'?idComment='+commentId+'&editComment=true');
	}
	else{	
		form.prop('action',form.prop('action')+'?idComment='+commentId);
	}
	
	collapseComment.collapse('show');
	textarea.focus();
	
    
    if(!originalHeading){
    	originalHeading = commentHeading.prop('textContent');
    }
    commentHeading.prop('innerHTML', heading);
}

/*
 * Reset the comment form to the initial position
 */
function resetForm(){
	commentHeading.prop('innerHTML', originalHeading);
	commentNew.insertAfter(commentNewAnchor);
	originalHeading = undefined;
	form.prop('action',originalURL);
	originalURL = undefined;
	textarea.prop('textContent','');
	button.prop('innerHTML',originalButton);
	originalButton = undefined;	
}

/*
 * Converts <p> tags to \n characters to be submitted
 */
function convertFromHTMLParagraph(data){
	data = data.replace(/<\/p><p>/g,'\n').replace(/<p>/g,'').replace(/<\/p>/g,'');		
	data = $('<div />').html(data).text();
	return data;
}

/*
 * Override collapse events to reset the comment form to the initial position
 * when user collapses (cancels edition). 
 */
collapseComment.on('hidden.bs.collapse', function () {
	if(originalHeading){
		resetForm();
    }	
})

/*
 * Override collapse events to focus on the text area when the comment form
 * is shown. 
 */

collapseComment.on('shown.bs.collapse', function () {
	textarea.focus();
})
    
    