# firew0rks

Play text art animations in your terminal! This package includes several pre-made animations like fireworks and a cozy fireplace.

![Eowzf_jWMAAk43x](https://github.com/user-attachments/assets/58d4c0ef-9f0b-49ae-80f0-4e12db3e34f0)

## Installation

```bash
git clone https://github.com/Axyl1410/firew0rks
```

## Usage
first you need to compile with ```javac``` (Java Compiler)

```bash
javac fireworks.java
```
and then
```bash
java fireworks [folder] [loops]
```

Parameters (all optional):
- `[folder]`: Folder containing text art frames (numbered 0.txt, 1.txt, etc.). Defaults to 'fireworks'
- `[loops]`: Number of times to loop the animation (-1 for infinite). Defaults to 20

## Examples

Run with defaults (fireworks animation, 20 loops):
```bash
java fireworks
```

Play the fireworks animation with custom loops:
```bash
java fireworks fireworks 3
```

Enjoy a cozy fireplace forever:
```bash
java fireworks fireplace -1
```

## Acknowledgments
This project is a Java version of [text_art_animations](https://github.com/rvizzz/text_art_animations) by [rvizzz](https://github.com/rvizzz). Thank you for the inspiration and the amazing ASCII art animations!

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/Axyl1410/firew0rks

## License

Available as open source under the terms of the [MIT License](https://opensource.org/licenses/MIT).
