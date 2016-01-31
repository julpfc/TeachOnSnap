var positions = {};

function currentPosition(questionID,initialPos){
	if(positions[questionID]==undefined){
		positions={};	
		positions[questionID]=initialPos;
	}
	return positions[questionID];
}


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

function showMoveControls(questionID) {
	hideMoveControls();
	$('#moveButton'+questionID).addClass('hidden');
	$('#saveButton'+questionID).removeClass('hidden');
	$('#cancelButton'+questionID).removeClass('hidden');
	$('#upButton'+questionID).removeClass('hidden');
	$('#downButton'+questionID).removeClass('hidden');	
}

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
