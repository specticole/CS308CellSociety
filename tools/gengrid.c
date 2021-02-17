#include <stdio.h>

int main(int argc, char *argv[]) {
  int w = atoi(argv[1]);
  int h = atoi(argv[2]);
  char *cell_type = argv[3];

  printf("<grid type=\"rectangular\" width=\"%d\" height=\"%d\" neighbors=\"8\" wrapping=\"false\">\n", w, h);
  for(int y = 0; y < h; y++) {
    printf("<gridrow>\n");
    for(int x = 0; x < w; x++) {
      printf("<gridcell>%s</gridcell>\n", cell_type);
    }
    printf("</gridrow>\n");
  }
  printf("</grid>\n");
}
