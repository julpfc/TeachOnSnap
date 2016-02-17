var positions = {};

/*
 * Store and return initial position for this question ID
 */
function currentPosition(questionID,initialPos){
	if(positions[questionID]==undefined){
		positions={};	
		positions[questionID]=initialPos;
	}
	return positions[questionID];
}

/*
 * Move this question ID up a position from the initial.
 * Move other questions to accomplish.
 */
function moveUp(questionID,initialPos) {
	var currentPos = currentPosition(questionID,initialPos);
	
	if(currentPos > 0){
		var nextPos = currentPos-1;
		if(currentPos > initialPos){
			nextPos = currentPos;
		}
		$('#panel'+initialPos).insertBefore($('#panel'+(nextPos)));
		positions[questionID]--;
	}
}

/*
 * Move this question ID down a position from the initial.
 * Move other questions to accomplish.
 */
function moveDown(questionID,initialPos,numQuestions) {	
	var currentPos = currentPosition(questionID,initialPos)
	
	if(currentPos < (numQuestions-1)){
		var nextPos = currentPos+1;
		if(currentPos < initialPos){
			nextPos = currentPos;
		}
		
		$('#panel'+initialPos).insertAfter($('#panel'+(nextPos)));
		positions[questionID]++;
	}
}

/*
 * Reset question position to the initial one.
 */
function restorePosition(questionID,initialPos){
	var currentPos = currentPosition(questionID, initialPos);
	positions[questionID] = initialPos;
	
	if(currentPos != initialPos){
		if(initialPos==0){
			$('#panel0').insertBefore($('#panel1'));
		}		
		else{
			$('#panel'+initialPos).insertAfter($('#panel'+(initialPos-1)));
		}
	}	
}

/*
 * Show controls to manage question position.
 */
function showMoveControls(questionID) {
	hideMoveControls();
	$('#moveButton'+questionID).addClass('hidden');
	$('#saveButton'+questionID).removeClass('hidden');
	$('#cancelButton'+questionID).removeClass('hidden');
	$('#upButton'+questionID).removeClass('hidden');
	$('#downButton'+questionID).removeClass('hidden');	
}

/*
 * Hide controls to manage question position.
 */
function hideMoveControls(questionID, initialPos) {
	if(questionID){		
		$('#saveButton'+questionID).addClass('hidden');
		$('#cancelButton'+questionID).addClass('hidden');
		$('#upButton'+questionID).addClass('hidden');
		$('#downButton'+questionID).addClass('hidden');
		restorePosition(questionID, initialPos);
		$("a[id^='moveButton']").removeClass('hidden');
	}
	else{
		$("a[id^='moveButton']").addClass('hidden');
		$("a[id^='saveButton']").addClass('hidden');
		$("a[id^='cancelButton']").addClass('hidden');
		$("a[id^='upButton']").addClass('hidden');
		$("a[id^='downButton']").addClass('hidden');
	}
}
