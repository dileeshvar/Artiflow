function showForm() {
	var oldURL = document.referrer;
	alert(oldURL);
	var element = document.getElementById("initForm");
	element.style.display = 'block';
}

function cancelForm() {
	this.form.reset();
	var element = document.getElementById("initForm");
	element.style.display = 'none';
}

function disableInOther() {
	var var_reviewers = document.getElementsByClassName("list_reviewers");
	var var_optReviewers = document.getElementsByClassName("list_optReviewers");
	var size = var_reviewers.length;
	for(var i = 0 ; i < size ; i++) {
		var_optReviewers[i].disabled = false;
		var_reviewers[i].disabled = false;
		if(var_reviewers[i].selected == true) {
			var_optReviewers[i].disabled = true;
		} 
		if(var_optReviewers[i].selected == true) {
			var_reviewers[i].disabled = true;
		}
	}
}