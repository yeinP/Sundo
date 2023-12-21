$("#file").on('change', function() {
	let files = $("#file")[0].files;
	let fileNames = [];

	for (let i = 0; i < files.length; i++) {
		fileNames.push(files[i].name);
	}

	if (fileNames.length > 1) {
		let remainingCount = fileNames.length - 1;
		$(".upload-name").val(fileNames[0] + " 외 " + remainingCount + "건");
	} else {
		$(".upload-name").val(fileNames[0]);
	}
});
