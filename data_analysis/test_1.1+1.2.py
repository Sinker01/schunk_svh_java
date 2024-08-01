import os
import pandas as pd
import matplotlib.pyplot as plt

# Folder containing the files
for folder_path in ('test_1.1', 'test_1.2'):

    # List all files in the folder
    files = [f for f in os.listdir(folder_path) if f.endswith('_measure.tsv')]

    # Loop through each file
    for file in files:
        # Read the TSV file into a DataFrame
        file_path = os.path.join(folder_path, file)
        df = pd.read_csv(file_path, sep='\t')

        # Extracting the title from the filename
        title = file.replace('_measure.tsv', '')

        # Create a plot
        fig, ax1 = plt.subplots(figsize=(10, 6))

        # Plot Position and Force on the primary y-axis
        ax1.scatter(df['time[ms]'], df['position'], s=1, label='Position', color='b')
        ax1.scatter(df['time[ms]'], df['force[N]'], s=1, label='Force[N]', color='g')
        ax1.set_xlabel('Time [ms]')
        ax1.set_ylabel('Position [N]/Force', color='k')
        ax1.tick_params(axis='y', labelcolor='k')

        # Create a secondary y-axis for Current
        ax2 = ax1.twinx()
        ax2.scatter(df['time[ms]'], df['current[mA]'], s=1, label='Current [mA]', color='r')
        ax2.set_ylabel('Current [mA]', color='r')
        ax2.tick_params(axis='y', labelcolor='r')

        # Add title and grid
        if folder_path == 'test_1.1':
            plt.title(f'Messungen von {title}')
        else:
            plt.title(f'Messungen mit Widerstand von {title}')
        fig.tight_layout()  # Adjust layout to make room for the labels
        ax1.grid(True)

        # Add legends
        ax1.legend(loc='upper left')
        ax2.legend(loc='upper right')

        # Save the plot as a PNG file
        plot_filename = os.path.join(folder_path, f'{title}_plot.png')
        plt.savefig(plot_filename)
        plt.close()

        print(f'Plot saved for {file} as {plot_filename}')
