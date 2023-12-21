let a_name = document.querySelectorAll('#a_name');

for (let t = 0; t < a_name.length; t++) {
	let name = a_name[t].textContent;
	let hidName = '';

	for (let i = 0; i < name.length; i++) {
		if (i != 0 && i != name.length - 1) {
			hidName += '*';
		} else {
			hidName += name[i];
		}
	}
	a_name[t].textContent = hidName;
}