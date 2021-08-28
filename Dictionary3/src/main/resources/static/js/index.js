const contentSearchBox = document.getElementById("boxSearching");

/*window.addEventListener('beforeunload', (event) => {
  // Cancel the event as stated by the standard.
  event.preventDefault();
  // Chrome requires returnValue to be set.
  event.returnValue = '';
});*/

contentSearchBox.addEventListener("change", debounce(() => {

	window.location.replace(`/search/${contentSearchBox.value}`);
}, 500));

/*contentSearchBox.addEventListener("input", debounce(() => {

	let word = contentSearchBox.value;

	if (word) {
		searchImage(contentSearchBox.value).then((image) => {
			tagImg = document.getElementById("images");
			let info = "";

			for (let i = 0; i < 10; i++) {
				if (image.photos[i].src.medium !== undefined) {
					info = info + `<img src=${image.photos[i].src.medium}>`;
				}
			}
			tagImg.innerHTML = info
		}).catch(e => console.error(e));
	}
}, 2000))*/



const searchImage = async function(texto) {
	const url = `https://api.pexels.com/v1/search?query=${texto}`;
	const respuesta = await fetch(url, {
		headers: {
			Authorization: "563492ad6f9170000100000165bf66e1016e4163b03ab06e5073d3c5",
		},
	});

	const response = await respuesta.json();
	return response;
};

function debounce(callback, wait) {
	let timerId;
	return (...args) => {
		clearTimeout(timerId);
		timerId = setTimeout(() => {
			callback(...args);
		}, wait);
	};
}
