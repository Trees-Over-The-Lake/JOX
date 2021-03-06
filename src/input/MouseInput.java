package input;

import entity.Entity;

public class MouseInput {
	// Coordenadas
		private static int x;
		private static int y;
		
		// Possíveis botoes do mouse
		private static boolean right_button_clicked;
		private static boolean right_button_pressed;
		
		// getters
		
		public static int get_x() {
			return x;
		}
		
		public static int get_y() {
			return y;
		}
		
		/**
		 * Gerar uma entidade do mouse, para fazer uma checagem
		 * de colisão
		 * @return
		 */
		public static Entity get_mouse_entity() {
			return new Entity(x,y,10,10);
		}
		
		public static boolean is_right_button_pressed() {
			return right_button_pressed;
		}
		
		public static boolean is_right_button_clicked() {
			boolean value = right_button_clicked;
			if(right_button_clicked) 
				right_button_clicked = false;
			return value;
		}

		//setters
		
		public static void set_x(int new_x) {
			x = new_x;
		}

		public static void set_y(int new_y) {
			y = new_y;
		}

		public static void set_right_button_clicked(boolean new_value) {
			right_button_clicked = new_value;
		}
		
		public static void set_right_button_pressed(boolean new_value) {
			right_button_pressed = new_value;
		}
		
		public static String data() {
			return get_x() + " " + get_y();
		}
}
