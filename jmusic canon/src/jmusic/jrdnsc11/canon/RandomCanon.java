package jmusic.jrdnsc11.canon;

import jm.JMC;
import jm.music.data.*;
import jm.util.*;
import jm.music.tools.*;


public class RandomCanon implements JMC {
	//Mode indicates what scale/mode the canon will be in
	//Currently set to Major Scale Degrees (this can be changed)
	static int[] mode = {0, 2, 4, 5, 7, 9, 11, 12};
	//length of piece
	/*--- MUTATION ---
	 * Changing the number of bars to = 10 and more greatly affects output
	 * Canon no longer a canon*/
	public static int bars = 4;
	//instrument part (only one) 
	//instrument can be changed 
	/*--- MUTATION ---
	 * Changing PIANO to FLUTE or other instruments changes the instrument
	 * Expected - see jMusic source code*/
	public static Part piano = new Part("Piano", PIANO, 0);


	public static void main(String[] args) {
		Score score = new Score("Random Canon");
		//Uses makeMelody to add to phrase
		//Start times can be changed (careful! can make the canon dissonant)
		Phrase phrase = makeMelody();
		phrase.setStartTime(0.0);
		piano.addPhrase(phrase);
		Mod.repeat(phrase, 1);
		Mod.transpose(phrase, 0);

		/*--- MUTATION ---
		 * Expected output is fulfilled: makeMelody with copy creates a new melody
		 * Phrase phrase1 = makeMelody().copy();*/
		Phrase phrase1 = phrase.copy();
		phrase1.setStartTime(4.0);
		piano.addPhrase(phrase1);
		Mod.repeat(phrase1, 1);
		Mod.transpose(phrase1, 0);

		/*--- ASSERTIONS ---
		 * assert phrase == phrase1;
		 * assert phrase == makeMelody();
		 * assert phrase1 == phrase.copy();*/

		/*--- MUTATION ---
		 * Expected output is fulfilled: makeMelody with copy creates a new melody
		 * Phrase phrase2 = makeMelody().copy();*/
		Phrase phrase2 = phrase.copy();
		phrase1.setStartTime(8.0);
		piano.addPhrase(phrase2);
		Mod.repeat(phrase2, 0);
		Mod.transpose(phrase1, 0);

		/*--- ASSERTIONS ---
		 * assert phrase == phrase2;
		 * assert phrase1 == phrase2;*/

		/*Section below can be added but it makes the canon sound
		dissonant - play around with the start time a bit. 12.0 is recommended

		Phrase phrase3 = phrase.copy();
		phrase1.setStartTime(12.0);
		piano.addPhrase(phrase3);
		Mod.repeat(phrase3, 2);
		Mod.transpose(phrase3, 0);*/

		//prints output
		score.addPart(piano);
		/*--- MUTATION ---
		 * Expected output is fulfilled: midi sound file only presents phrase, ignores other
		 * conditions
		 * Write.midi(phrase, "canon.mid");*/
		Write.midi(score, "canon.mid");
		View.notate(score);
		View.show(score);

	}

	/*Makes a random melody based on the given mode
	  Problem: it is too random! Potential change would be to make it a chord progression*/
	public static Phrase makeMelody() {
		Phrase phr = new Phrase();
		/*--- MUTATION --- 
		 * 1x compiler mutation, temp can't be converted to int later if it is double
		 * Thus: Expected output is fulfilled
		 * double temp, newPitch;*/
		int temp, newPitch;
		int prevPitch = 60;//starts at Middle C


		/*--- ASSERTIONS ---
		 * assertSame(prevPitch, 60);
		 * assertNotNull(60);*/

		for(short i=0;i<bars*8-3;) {
			temp=(int)(Math.random()*14-7);
			newPitch = prevPitch + temp;
			/*--- MUTATION ---
			 * Expected output is fulfilled: Melody changes direction (becomes dissonant)
			 * newPitch = prevPitch - temp;*/
			for (short j=0; j<mode.length;j++) {
				if(newPitch%12 == mode[j]) {
					if(i==bars*8-4) {
						Note n=new Note(newPitch, M,
								(int)(Math.random()*50+60));
						phr.addNote(n);
					} else {
						Note n= new Note(newPitch, Q,
								(int)(Math.random()*50+60));
						/*--- MUTATION ---
						 * expected output is fulfilled
						 * Melody eventually begins to climb and doesn't appear to go down
						 * No longer a canon
						 * (int)(Math.random()*50+100));*/
						phr.addNote(n);
					}
					/*--- MUTATION ---
					 * output is expected 
					 * pitch is lowered, still a canon, but it's 'barely' recognisable
					 * prevPitch=temp;*/
					prevPitch=newPitch;
					/*--- MUTATION ---
					 * Expected output is fulfilled
					 * No output prints
					 * i --;*/
					i++;
					/*--- ASSERTION ---
					 * assert i == bars*8-4;*/
				}
			}
		}
		piano.addPhrase(phr);
		return phr;

	}

}



