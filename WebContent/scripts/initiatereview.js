function addMoreFiles() {
	
}

function showForm() {
	var element = document.getElementById("initForm");
	element.style.display = 'block';
}

function cancelForm() {
	this.form.reset();
	var element = document.getElementById("initForm");
	element.style.display = 'none';
}