import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
// import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgxSpinnerModule } from "ngx-spinner";
import { NgxSpinnerService } from "ngx-spinner";
import { JutsuHttpService } from './jutsu-http.service';
import { jutsuInfo } from './jutsuInfo';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, NgxSpinnerModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'jutsu-generator-client';
  seals: string;
  selectedJutsu: jutsuInfo | undefined;
  imageUrl: string | undefined;
  currentImage: string;
  sealImagesSequence: string[];
  currentSealIndex: number;
  imageMappings: { [key: string]: string } = {
    'rat': 'assets/rat.png',
    'ox': 'assets/ox.png',
    'tiger': 'assets/tiger.png',
    'hare': 'assets/rabbit.png',
    'bird': 'assets/bird.png',
    'boar': 'assets/boar.png',
    'dog': 'assets/dog.png',
    'dragon': 'assets/dragon.png',
    'serpent': 'assets/snake.png',
    'horse': 'assets/horse.png',
    'monkey': 'assets/monkey.png',
    'ram': 'assets/sheep.png'
  };
  KOHONHA_IMAGE: string = "https://seeklogo.com/images/K/konoha_leaf-logo-277E238E29-seeklogo.com.png";

  constructor(
    private jutsuHttp: JutsuHttpService,
    private spinner: NgxSpinnerService) {
    this.seals = '';
    this.currentSealIndex = -1;
    this.imageUrl = undefined;
    this.currentImage = this.KOHONHA_IMAGE;
    this.selectedJutsu = undefined;
    this.sealImagesSequence = [];
  }

  reset() {
    this.seals = '';
    this.currentSealIndex = -1;
    this.imageUrl = undefined;
    this.currentImage = this.KOHONHA_IMAGE;
    this.selectedJutsu = undefined;
    this.sealImagesSequence = [];
  }


  appendSeal(seal: string) {
    if (this.seals == '') {
      this.seals = seal;
    } else {
      this.seals += ', ' + seal;
    }
    this.sealImagesSequence.push(this.imageMappings[seal]);
  }

  createJutsu() {
    this.jutsuHttp.createJutsu(this.seals).subscribe(data => {
      this.selectedJutsu = data;
    })
  }

  weaveSeals() {
    if (this.selectedJutsu) {
      this.spinner.show();
      this.jutsuHttp.generateImage(this.selectedJutsu).subscribe(async data => {
        this.imageUrl = undefined;
        this.spinner.hide();
        this.currentSealIndex = 0;
        while (this.currentSealIndex < this.sealImagesSequence.length) {
          this.currentImage = this.sealImagesSequence[this.currentSealIndex];
          await new Promise(f => setTimeout(f, calcDelay(this.sealImagesSequence.length)));
          this.currentSealIndex++;
        }
        // stop displaying the seals, then display generated image
        this.currentSealIndex = -1;
        this.currentImage = data.generatedImageUrl;
      })
    }

  }
}
function calcDelay(numImages: number): number | undefined {
  if (numImages <= 5) return -50*numImages + 450;
  return 350;
}

