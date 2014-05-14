function deleteFile(fileName, obj) {
	$(".deleteFile").click(function() {
		$(this).closest(".deletable").remove();
	});
	xhr = new XMLHttpRequest();
	var fileData = new FormData();
	fileData.append("File", fileName);
	xhr.open("post", "DeleteServlet", true);
	xhr.send(fileData);
	var fileList = document.getElementById("file-list");
	alert(fileList);
	if(!fileList.hasChildNodes())
		document.getElementById("noItems").style.visibility = 'block';
}

