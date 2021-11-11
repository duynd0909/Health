export function preloaderFinished() {
  const body = document.querySelector("body");
  const preloader = document.querySelector(".preloader");
  if (!body) {
    return;
  }

  setTimeout(() => {
    if (!preloader) {
      return;
    }
    preloader.addEventListener("transitionend", () => {
      preloader.className = "preloader-hidden";
    });
    preloader.className += " preloader-hidden-add preloader-hidden-add-active";
    body.style.overflow = "";
    body.style.overflow = "";
  }, 100);
}
