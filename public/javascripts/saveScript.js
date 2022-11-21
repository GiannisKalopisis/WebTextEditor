const SAVING_MESSAGE = "Saving...";
const SAVED_MESSAGE = "All changes saved.";
const NOT_SAVED_MESSAGE = "Couldn't save your text.";
const NOT_SAVED_MESSAGE_YET = "Your text is not saved yet";
const SAVE_AFTER_X_TIMES = 1
let saved = false;

document.querySelectorAll('.autosave-message')
    .forEach((el) => (el.textContent = SAVED_MESSAGE));

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('[data-autosave-url]').forEach((inputField) => {
        const autosaveMessageEl = inputField
            .closest('.container')
            .querySelector('.autosave-message');
        let counter = 1;
        inputField.addEventListener('input', async () => {
            autosaveMessageEl.setAttribute('style', 'white-space: pre;');
            autosaveMessageEl.classList.add('autosave-message--not-saved')
            autosaveMessageEl.classList.add('autosave-message--not-saved-yet')
            autosaveMessageEl.textContent = NOT_SAVED_MESSAGE_YET + ".\r\n Type " + (5-counter) + " more times." ;

            if (counter === SAVE_AFTER_X_TIMES){
                counter = 0;
                autosaveMessageEl.classList.remove('autosave-message--not-saved-yet')
                await saveText(inputField);
                if (saved) {
                    autosaveMessageEl.classList.remove('autosave-message--not-saved')
                    autosaveMessageEl.textContent = SAVED_MESSAGE;
                }
                else {
                    autosaveMessageEl.textContent = NOT_SAVED_MESSAGE;
                }
            }

            /* Add here save button */

            counter++;
        })
    });
});

async function saveText(inputField) {
    const name = inputField.getAttribute("name");
    const value = inputField.value;
    const url = inputField.dataset.autosaveUrl;
    const autosaveMessageEl = inputField
        .closest('.container')
        .querySelector('.autosave-message');

    autosaveMessageEl.classList.remove('autosave-message--not-saved')
    autosaveMessageEl.classList.add('autosave-message--saving');
    autosaveMessageEl.textContent = SAVING_MESSAGE;

    const response = await fetch(url, {
        method: 'POST',
        headers:{
            contentType: 'application/json'
        },
        body: JSON.stringify({'title': name, 'text' : value}),
    });

    autosaveMessageEl.classList.remove('autosave-message--saving');
    if (response.status === 200) {
        saved = true
        autosaveMessageEl.textContent = SAVED_MESSAGE;
    } else {
        saved = false
        autosaveMessageEl.classList.add('autosave-message--not-saved')
        autosaveMessageEl.textContent = NOT_SAVED_MESSAGE;
    }
}